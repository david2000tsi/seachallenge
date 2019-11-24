package com.app.seachallenge.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.dto.FuncionarioDTO;
import com.app.seachallenge.model.Atividade;
import com.app.seachallenge.model.FuncionarioAtividadeEPI;
import com.app.seachallenge.model.Cargo;
import com.app.seachallenge.model.EPI;
import com.app.seachallenge.model.Funcionario;
import com.app.seachallenge.repository.FuncionarioAtividadeEPIRepository;
import com.app.seachallenge.repository.AtividadeRepository;
import com.app.seachallenge.repository.CargoRepository;
import com.app.seachallenge.repository.EPIRepository;
import com.app.seachallenge.repository.FuncionarioRepository;

@RestController
@Component
@Transactional
public class FuncionarioController extends UtilsController {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private AtividadeRepository atividadeRepository;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private EPIRepository epiRepository;
	
	@Autowired
	private FuncionarioAtividadeEPIRepository funcionarioAtividadeEPIRepository;
	
	public byte[] fromStringToByte(String image) {
		try {
			if(image != null && image.length() > 0) {
				return Base64.getDecoder().decode(image);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public String fromByteToString(byte[] image) {
		try {
			if(image != null && image.length > 0) {
				return Base64.getEncoder().encodeToString(image);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	@RequestMapping(value = "/funcionario/save", method = RequestMethod.POST)
	public String save(@RequestParam("data") String data) {
		String result = getErrorJson(-1, "SAVE_FUNCIONARIO_NOT_FINISHED");
		boolean doRollback = false;
		try {
			JSONObject json = new JSONObject(data);
			JSONObject jsonFuncionario = json.getJSONObject("funcionario");
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			funcionarioDTO.setNome(jsonFuncionario.getString("nome"));
			funcionarioDTO.setCpf(jsonFuncionario.getString("cpf"));
			funcionarioDTO.setRg(jsonFuncionario.getString("rg"));
			funcionarioDTO.setSexo(jsonFuncionario.getString("sexo"));
			funcionarioDTO.setDataNascimento(new Date(jsonFuncionario.getString("dataNascimento")));
			funcionarioDTO.setAtivo(jsonFuncionario.getBoolean("ativo"));
			funcionarioDTO.setAtestadoSaude(fromStringToByte(jsonFuncionario.getString("atestadoSaude")));
			
			JSONObject jsonCargo = jsonFuncionario.getJSONObject("cargo");
			long idCargo = jsonCargo.getInt("id");
			Optional<Cargo> optionalCargo = cargoRepository.findById(idCargo);
			if(optionalCargo.isPresent()) {
				Cargo cargo = optionalCargo.get();
				funcionarioDTO.setCargo(cargo);
			}
			
			if(funcionarioDTO.isValidToSave()) {
				Funcionario funcionario = funcionarioDTO.build();
				
				// Persist!
				funcionarioRepository.save(funcionario);

				JSONArray jsonAtividadeEpis = jsonFuncionario.getJSONArray("atividadeepis");
				for(int i = 0; i < jsonAtividadeEpis.length(); i++) {
					JSONObject jsonAtividadeEpi = jsonAtividadeEpis.getJSONObject(i);
					long idAtividade = jsonAtividadeEpi.getInt("atividade_id");
					long idEPI = jsonAtividadeEpi.getInt("epi_id");
					long numeroCA = jsonAtividadeEpi.getInt("numeroCA");
					
					Optional<Atividade> optionalAtividade = atividadeRepository.findById(idAtividade);
					Optional<EPI> optionalEPI = epiRepository.findById(idEPI);;
					
					// If epi is presente so numeroca is mandatory.
					boolean epiInformed = false;
					boolean epiDataError = false;
					if(idEPI > 0) {
						epiInformed = true;
						if(numeroCA == 0) {
							epiDataError = true;
						}
					}
					
					if(epiInformed && optionalEPI.isPresent() == false) {
						result = getErrorJson(-1, "SAVE_FUNCIONARIO_NO_EPI");
						doRollback = true;
					} else if(epiDataError) {
						result = getErrorJson(-1, "SAVE_FUNCIONARIO_NO_NUM_CA");
						doRollback = true;
					} else if(optionalAtividade.isPresent() == false) {
						result = getErrorJson(-1, "SAVE_FUNCIONARIO_NO_ATIVIDADE");
						doRollback = true;
					} else {
						Atividade atividade = optionalAtividade.get();
						EPI epi = optionalEPI.get();
						
						FuncionarioAtividadeEPI funcionarioAtividadeEPI = new FuncionarioAtividadeEPI();
						funcionarioAtividadeEPI.setFuncionario(funcionario);
						funcionarioAtividadeEPI.setAtividade(atividade);
						
						if(epiDataError == false) {
							funcionarioAtividadeEPI.setEpi(epi);
							funcionarioAtividadeEPI.setNumeroCA(numeroCA);
						}
						
						// Persist!
						funcionarioAtividadeEPIRepository.save(funcionarioAtividadeEPI);
					}
					
					if(doRollback) {
						break;
					}
				}
				
				if(doRollback) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					throw new Exception("SAVE_FUNCIONARIO_BAD_PROCESSING");
				}
				
				result = getErrorJson(0, "SAVE_FUNCIONARIO_SUCCESS");
			} else {
				result = getErrorJson(-1, "SAVE_FUNCIONARIO_INVALID_DATA");
			}
		} catch(Exception e) {
			e.printStackTrace();
			result = getErrorJson(-1, "SAVE_FUNCIONARIO_ERR");
		}

		return result;
	}
	
	private JSONObject getJSONFuncionario(Funcionario funcionario) {
		JSONObject jsonFuncionario = new JSONObject();
		jsonFuncionario.put("id", funcionario.getId());
		jsonFuncionario.put("nome", funcionario.getNome());
		jsonFuncionario.put("cpf", funcionario.getCpf());
		jsonFuncionario.put("rg", funcionario.getRg());
		jsonFuncionario.put("sexo", funcionario.getSexo());
		jsonFuncionario.put("dataNascimento", funcionario.getDataNascimento());
		jsonFuncionario.put("ativo", funcionario.isAtivo());
		jsonFuncionario.put("atestadoSaude", fromByteToString(funcionario.getAtestadoSaude()));

		jsonFuncionario.put("cargo_id", funcionario.getCargo().getId());
		jsonFuncionario.put("cargo_nome", funcionario.getCargo().getNome());
		
		// Add additional data!!!
		ArrayList<JSONObject> listFuncionarioAtividadeEPI = new ArrayList<JSONObject>();
		for(FuncionarioAtividadeEPI funcionarioAtividadeEPI : funcionario.getFuncionarioAtividadeEPIs()) {
			JSONObject jsonFuncionarioAtividadeEPI = new JSONObject();
			
			jsonFuncionarioAtividadeEPI.put("atividade", funcionarioAtividadeEPI.getAtividade().getNome());
			jsonFuncionarioAtividadeEPI.put("epi", funcionarioAtividadeEPI.getEpi().getNome());
			jsonFuncionarioAtividadeEPI.put("numeroCA", funcionarioAtividadeEPI.getNumeroCA());

			listFuncionarioAtividadeEPI.add(jsonFuncionarioAtividadeEPI);
		}
		
		jsonFuncionario.put("atividadeepis", listFuncionarioAtividadeEPI);
		
		return jsonFuncionario;
	}
	
	@RequestMapping(value = {"/funcionario/get/all", "/funcionario/get/all/{param}"}, method = RequestMethod.GET)
	public String geAll(@PathVariable(value = "param", required = false) String param) {
		String result = "";
		try {
			Iterable<Funcionario> funcionarios = null;
			if(param != null) {
				if(param.equals("ativo")) {
					funcionarios = funcionarioRepository.findByAtivoTrue();
				} else if(param.equals("inativo")) {
					funcionarios = funcionarioRepository.findByAtivoFalse();
				}
			} else {
				funcionarios = funcionarioRepository.findAll();
			}
			ArrayList<JSONObject> jsonFuncionarios = new ArrayList<JSONObject>();
			for(Funcionario funcionario : funcionarios) {
				JSONObject jsonFuncionario = getJSONFuncionario(funcionario);
				
				jsonFuncionarios.add(jsonFuncionario);
			}
			
			JSONObject response = new JSONObject();
			response.put("funcionarios", jsonFuncionarios);
			result = response.toString();
		} catch(Exception e) {
			e.printStackTrace();
			result = getErrorJson(-1, "GET_ALL_FUNCIONARIO_ERR");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/funcionario/get/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable("id") long id) {
		String result = "";
		try {
			if(id < 1L) {
				return getErrorJson(-1, "GET_FUNCIONARIO_BAD_PARAM");
			}
			Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
			if(optionalFuncionario.isPresent()) {
				Funcionario funcionario = optionalFuncionario.get();
				JSONObject jsonFuncionario = getJSONFuncionario(funcionario);
				JSONObject response = new JSONObject();
				response.put("funcionario", jsonFuncionario);
				result = response.toString();
			} else {
				result = getErrorJson(-1, "GET_FUNCIONARIO_NO_DATA");
			}
		} catch(Exception e) {
			e.printStackTrace();
			result = getErrorJson(-1, "GET_FUNCIONARIO_ERR");
		}
		
		return result;
	}
	
	public boolean isFuncionarioAtividadeEPISameData(FuncionarioAtividadeEPI first, FuncionarioAtividadeEPI second) {
		boolean result = false;
		if(first != null && second != null) {
			result = (first.getFuncionario().getId() == second.getFuncionario().getId() &&
					first.getAtividade().getId() == second.getAtividade().getId() &&
					first.getEpi().getId() == second.getEpi().getId() &&
					first.getNumeroCA() == second.getNumeroCA());
		}
		return result;
	}
	
	public boolean existsFuncionarioAtividadeEPIInList(List<FuncionarioAtividadeEPI> list, FuncionarioAtividadeEPI value) {
		for(FuncionarioAtividadeEPI reg : list) {
			if(isFuncionarioAtividadeEPISameData(reg, value)) {
				return true;
			}
		}
		return false;
	}
	
	public void removeIfExistsFuncionarioAtividadeEPIInList(List<FuncionarioAtividadeEPI> list, FuncionarioAtividadeEPI value) {
		for(int i = 0; i < list.size(); i++) {
			FuncionarioAtividadeEPI reg = list.get(i);
			if(isFuncionarioAtividadeEPISameData(reg, value)) {
				list.remove(i);
			}
		}
	}
	
	public List<FuncionarioAtividadeEPI> funcionarioAtividadeEPIToList(Iterable<FuncionarioAtividadeEPI> iterable) {
		ArrayList<FuncionarioAtividadeEPI> list = new ArrayList<FuncionarioAtividadeEPI>();
		for(FuncionarioAtividadeEPI reg : iterable) {
			list.add(reg);
		}
		return list;
	}
	
	public boolean existsFuncionarioAtividadeEPIInDatabase(long funcionarioId, long atividadeId, long epiId, long numeroCA) {
		Iterable<FuncionarioAtividadeEPI> iterableFuncionarioAtividadeEPIAlreadyInDatabase =
				funcionarioAtividadeEPIRepository.findByFuncionarioIdAndAtividadeIdAndEpiIdAndNumeroCA
				(funcionarioId, atividadeId, epiId, numeroCA);

		ArrayList<FuncionarioAtividadeEPI> listFuncionarioAtividadeEPIAlreadyInDatabase = new ArrayList<FuncionarioAtividadeEPI>();
		for(FuncionarioAtividadeEPI reg : iterableFuncionarioAtividadeEPIAlreadyInDatabase) {
			listFuncionarioAtividadeEPIAlreadyInDatabase.add(reg);
		}
		
		return (listFuncionarioAtividadeEPIAlreadyInDatabase.size() > 0);
	}
	
	@RequestMapping(value = "/funcionario/update", method = RequestMethod.PUT)
	public String updateById(@RequestParam("data") String data) {
		String result = "";
		boolean doRollback = false;
		try {
			if(data == null || data.length() == 0) {
				return getErrorJson(-1, "UPDATE_FUNCIONARIO_BAD_PARAM");
			}
			
			JSONObject json = new JSONObject(data);
			JSONObject jsonFuncionario = json.getJSONObject("funcionario");
			long id = jsonFuncionario.getInt("id");
			if(id < 1L) {
				return getErrorJson(-1, "UPDATE_FUNCIONARIO_BAD_PARAM");
			}

			Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
			if(optionalFuncionario.isPresent()) {
				Funcionario funcionario = optionalFuncionario.get();
				
				FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
				funcionarioDTO.setNome(jsonFuncionario.getString("nome"));
				funcionarioDTO.setCpf(jsonFuncionario.getString("cpf"));
				funcionarioDTO.setRg(jsonFuncionario.getString("rg"));
				funcionarioDTO.setSexo(jsonFuncionario.getString("sexo"));
				funcionarioDTO.setDataNascimento(new Date(jsonFuncionario.getString("dataNascimento")));
				funcionarioDTO.setAtivo(jsonFuncionario.getBoolean("ativo"));
				funcionarioDTO.setAtestadoSaude(fromStringToByte(jsonFuncionario.getString("atestadoSaude")));
				
				JSONObject jsonCargo = jsonFuncionario.getJSONObject("cargo");
				long idCargo = jsonCargo.getInt("id");
				Optional<Cargo> optionalCargo = cargoRepository.findById(idCargo);
				if(optionalCargo.isPresent()) {
					Cargo cargo = optionalCargo.get();
					funcionarioDTO.setCargo(cargo);
				}
				
				if(funcionarioDTO.isValidToSave()) {
					funcionario.setNome(funcionarioDTO.getNome());
					funcionario.setCpf(funcionarioDTO.getCpf());
					funcionario.setRg(funcionarioDTO.getRg());
					funcionario.setSexo(funcionarioDTO.getSexo());
					funcionario.setDataNascimento(funcionarioDTO.getDataNascimento());
					funcionario.setAtivo(funcionarioDTO.isAtivo());
					funcionario.setAtestadoSaude(funcionarioDTO.getAtestadoSaude());
					funcionario.setCargo(funcionarioDTO.getCargo());
					
					// Persist!
					funcionarioRepository.save(funcionario);
					
					Iterable<FuncionarioAtividadeEPI> iterableFuncionarioAtividadeEpisAlreadyInDatabase = funcionarioAtividadeEPIRepository.findByFuncionarioId(funcionario.getId());
					ArrayList<FuncionarioAtividadeEPI> funcionarioAtividadeEpisAlreadyInDatabase = new ArrayList<FuncionarioAtividadeEPI>();
					for(FuncionarioAtividadeEPI funcionarioAtividadeEpi : iterableFuncionarioAtividadeEpisAlreadyInDatabase) {
						funcionarioAtividadeEpisAlreadyInDatabase.add(funcionarioAtividadeEpi);
					}
					
					// Update atividades, epis, numerocas...
					JSONArray jsonAtividadeEpis = jsonFuncionario.getJSONArray("atividadeepis");
					for(int i = 0; i < jsonAtividadeEpis.length(); i++) {
						JSONObject jsonAtividadeEpi = jsonAtividadeEpis.getJSONObject(i);
						long idAtividade = jsonAtividadeEpi.getInt("atividade_id");
						long idEPI = jsonAtividadeEpi.getInt("epi_id");
						long numeroCA = jsonAtividadeEpi.getInt("numeroCA");
						
						Optional<Atividade> optionalAtividade = atividadeRepository.findById(idAtividade);
						Optional<EPI> optionalEPI = epiRepository.findById(idEPI);;
						
						// If epi is presente so numeroca is mandatory.
						boolean epiInformed = false;
						boolean epiDataError = false;
						if(idEPI > 0) {
							epiInformed = true;
							if(numeroCA == 0) {
								epiDataError = true;
							}
						}
						
						if(epiInformed && optionalEPI.isPresent() == false) {
							result = getErrorJson(-1, "UPDATE_FUNCIONARIO_NO_EPI");
							doRollback = true;
						} else if(epiDataError) {
							result = getErrorJson(-1, "UPDATE_FUNCIONARIO_NO_NUM_CA");
							doRollback = true;
						} else if(optionalAtividade.isPresent() == false) {
							result = getErrorJson(-1, "UPDATE_FUNCIONARIO_NO_ATIVIDADE");
							doRollback = true;
						} else {
							Atividade atividade = optionalAtividade.get();
							EPI epi = optionalEPI.get();

							FuncionarioAtividadeEPI funcionarioAtividadeEPI = new FuncionarioAtividadeEPI();
							funcionarioAtividadeEPI.setFuncionario(funcionario);
							funcionarioAtividadeEPI.setAtividade(atividade);
							
							if(epiDataError == false) {
								funcionarioAtividadeEPI.setEpi(epi);
								funcionarioAtividadeEPI.setNumeroCA(numeroCA);
							}
							
							// Already exists, not necessary to insert...
							// Otherwise, case not exists let's go to insert it.
							if(existsFuncionarioAtividadeEPIInDatabase(funcionario.getId(), atividade.getId(), epi.getId(), numeroCA) == false) {
								// Persist!
								funcionarioAtividadeEPIRepository.save(funcionarioAtividadeEPI);
							}
							
							// Remove the received from the original list of data from database.
							removeIfExistsFuncionarioAtividadeEPIInList(funcionarioAtividadeEpisAlreadyInDatabase, funcionarioAtividadeEPI);
						}
						
						if(doRollback) {
							break;
						}
					}
					
					// Remove from database the records that not was received in the update request.
					if(funcionarioAtividadeEpisAlreadyInDatabase.size() > 0) {
						for(FuncionarioAtividadeEPI funcionarioAtividadeEpi : funcionarioAtividadeEpisAlreadyInDatabase) {
							funcionarioAtividadeEPIRepository.delete(funcionarioAtividadeEpi);
						}
					}
					
					if(doRollback) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						throw new Exception("UPDATE_FUNCIONARIO_BAD_PROCESSING");
					}
					
					result = getErrorJson(0, "UPDATE_FUNCIONARIO_SUCCESS");
				}
			} else {
				result = getErrorJson(-1, "UPDATE_FUNCIONARIO_NO_DATA");
			}
		} catch(Exception e) {
			e.printStackTrace();
			result = getErrorJson(-1, "UPDATE_FUNCIONARIO_ERR");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/funcionario/delete/{id}", method = RequestMethod.DELETE)
	public String deleteById(@PathVariable("id") long id) {
		String result = "";
		try {
			if(id < 1L) {
				return getErrorJson(-1, "DELETE_FUNCIONARIO_BAD_PARAM");
			}
			Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
			if(optionalFuncionario.isPresent()) {
				Funcionario funcionario = optionalFuncionario.get();
				
				Iterable<FuncionarioAtividadeEPI> funcionarioAtividadeEpis = funcionarioAtividadeEPIRepository.findByFuncionarioId(funcionario.getId());
				for(FuncionarioAtividadeEPI funcionarioAtividadeEpi : funcionarioAtividadeEpis) {
					funcionarioAtividadeEPIRepository.delete(funcionarioAtividadeEpi);
				}
				
				funcionarioRepository.delete(funcionario);
				
				result = getErrorJson(0, "DELETE_FUNCIONARIO_SUCCESS");
			} else {
				result = getErrorJson(-1, "DELETE_FUNCIONARIO_NO_DATA");
			}
		} catch(Exception e) {
			e.printStackTrace();
			result = getErrorJson(-1, "DELETE_FUNCIONARIO_ERR");
		}
		
		return result;
	}
}
