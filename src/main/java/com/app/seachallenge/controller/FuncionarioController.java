package com.app.seachallenge.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.dto.FuncionarioDTO;
import com.app.seachallenge.model.Atividade;
import com.app.seachallenge.model.AtividadeEPI;
import com.app.seachallenge.model.Cargo;
import com.app.seachallenge.model.EPI;
import com.app.seachallenge.model.Funcionario;
import com.app.seachallenge.model.FuncionarioAtividade;
import com.app.seachallenge.repository.AtividadeEPIRepository;
import com.app.seachallenge.repository.AtividadeRepository;
import com.app.seachallenge.repository.CargoRepository;
import com.app.seachallenge.repository.EPIRepository;
import com.app.seachallenge.repository.FuncionarioAtividadeRepository;
import com.app.seachallenge.repository.FuncionarioRepository;

@RestController
@Component
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
	private AtividadeEPIRepository atividadeEPIRepository;
	
	@Autowired
	private FuncionarioAtividadeRepository funcionarioAtividadeRepository;
	
	@RequestMapping(value = "/funcionario/save", method = RequestMethod.POST)
	@Transactional
	public String save(@RequestParam("data") String data) {
		String result = getErrorJson(-1, "SAVE_FUNCIONARIO_NOT_FINISHED");
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
			funcionarioDTO.setAtestadoSaude(null);
			
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

				JSONArray jsonAtividades = jsonFuncionario.getJSONArray("atividades");
				for(int i = 0; i < jsonAtividades.length(); i++) {
					JSONObject jsonAtividade = jsonAtividades.getJSONObject(i);
					long idAtividade = jsonAtividade.getInt("id");
					Optional<Atividade> optionalAtividade = atividadeRepository.findById(idAtividade);
					
					if(optionalAtividade.isPresent()) {
						Atividade atividade = optionalAtividade.get();
						// Persist!
						atividadeRepository.save(atividade);
						
						FuncionarioAtividade funcionarioAtividade = new FuncionarioAtividade();
						funcionarioAtividade.setFuncionario(funcionario);
						funcionarioAtividade.setAtividade(atividade);
						
						// Persist!
						funcionarioAtividadeRepository.save(funcionarioAtividade);
						
						JSONArray jsonEPIs = jsonAtividade.getJSONArray("epis");
						for(int j = 0; j < jsonEPIs.length(); j++) {
							JSONObject jsonEPI = jsonEPIs.getJSONObject(j);
							long idEPI = jsonEPI.getInt("id");
							long numeroCA = jsonEPI.getInt("numeroCA");
							
							Optional<EPI> optionalEPI = epiRepository.findById(idEPI);
							if(optionalEPI.isPresent()) {
								EPI epi = optionalEPI.get();
								AtividadeEPI atividadeEPI = new AtividadeEPI();
								
								atividadeEPI.setAtividade(atividade);
								atividadeEPI.setEpi(epi);
								atividadeEPI.setNumeroCA(numeroCA);
								
								// Persist!
								epiRepository.save(epi);
								atividadeEPIRepository.save(atividadeEPI);
								
								result = getErrorJson(-1, "SAVE_FUNCIONARIO_SUCCESS");
							}
						}
					}
				}
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
		jsonFuncionario.put("atestadoSaude", funcionario.getAtestadoSaude());
		
		return jsonFuncionario;
	}
	
	@RequestMapping(value = "/funcionario/get/all", method = RequestMethod.GET)
	public String geAllt() {
		String result = "";
		try {
			Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
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
}
