package com.app.seachallenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.seachallenge.dto.FuncionarioDTO;
import com.app.seachallenge.model.Atividade;
import com.app.seachallenge.model.Cargo;
import com.app.seachallenge.model.EPI;
import com.app.seachallenge.model.Funcionario;
import com.app.seachallenge.model.FuncionarioAtividadeEPI;
import com.app.seachallenge.repository.AtividadeRepository;
import com.app.seachallenge.repository.CargoRepository;
import com.app.seachallenge.repository.EPIRepository;
import com.app.seachallenge.repository.FuncionarioAtividadeEPIRepository;
import com.app.seachallenge.repository.FuncionarioRepository;
import com.app.seachallenge.util.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class FuncionarioService {
	
	private final FuncionarioRepository funcionarioRepository;
	private final AtividadeRepository atividadeRepository;
	private final CargoRepository cargoRepository;
	private final EPIRepository epiRepository;
	private final FuncionarioAtividadeEPIRepository funcionarioAtividadeEPIRepository;
	private final MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();
	
	private void createAtividadeEPI(Funcionario funcionario, Long atividadeId, Long epiId, Long numeroCA) {
		Optional<Atividade> optionalAtividade = atividadeRepository.findById(atividadeId);
		Optional<EPI> optionalEPI = epiRepository.findById(epiId);
		if(optionalAtividade.isPresent() && optionalEPI.isPresent()) {
			FuncionarioAtividadeEPI funcionarioAtividadeEPI = new FuncionarioAtividadeEPI();
			funcionarioAtividadeEPI.setFuncionario(funcionario);
			funcionarioAtividadeEPI.setAtividade(optionalAtividade.get());
			funcionarioAtividadeEPI.setEpi(optionalEPI.get());
			funcionarioAtividadeEPI.setNumeroCA(numeroCA);
			this.funcionarioAtividadeEPIRepository.save(funcionarioAtividadeEPI);
		}
	}
	
	@Transactional(rollbackOn = Exception.class)
	public FuncionarioDTO create(FuncionarioDTO funcionarioDTO) {
		try {
			funcionarioDTO.setAtestadoSaude(Utils.fromStringToByte(funcionarioDTO.getAtestadoSaudeBase64()));
			Funcionario funcionario = this.mapper.map(funcionarioDTO, Funcionario.class);
			
			Optional<Cargo> optionalCargo = cargoRepository.findById(funcionarioDTO.getCargo().getId());
			if(optionalCargo.isPresent()) {
				Cargo cargo = optionalCargo.get();
				funcionario.setCargo(cargo);
			}
			
			funcionario = this.funcionarioRepository.save(funcionario);
			final Funcionario fnc = funcionario;
			funcionarioDTO.getStividadesEpis().stream().forEach(at -> {
				this.createAtividadeEPI(fnc, at.getAtividadeId(), at.getEpiId(), at.getNumeroCA());
			});
			
			funcionario = this.funcionarioRepository.findById(funcionario.getId()).get();
			return this.mapper.map(funcionario, FuncionarioDTO.class);
		} catch(Exception e) {
			final String msg = "Error create funcionario";
			FuncionarioService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public List<FuncionarioDTO> getAll(Boolean ativo) {
		try {
			List<Funcionario> funcionarios = new ArrayList<>();
			if(Objects.nonNull(ativo)) {
				if(Boolean.TRUE.equals(ativo)) {
					funcionarios.addAll(this.funcionarioRepository.findByAtivoTrue());
				} else {
					funcionarios.addAll(this.funcionarioRepository.findByAtivoFalse());
				}
			} else {
				funcionarios.addAll(this.funcionarioRepository.findAll());
			}
			return this.mapper.mapAsList(funcionarios, FuncionarioDTO.class);
		} catch(Exception e) {
			final String msg = "Error get funcionarios";
			FuncionarioService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public FuncionarioDTO getOne(Long id) {
		try {
			if(Objects.isNull(id) || id < 1L) {
				throw new IllegalArgumentException("Invalid identifier");
			}
			Optional<Funcionario> optionalFuncionario = this.funcionarioRepository.findById(id);
			if(optionalFuncionario.isPresent()) {
				return this.mapper.map(optionalFuncionario.get(), FuncionarioDTO.class);
			} else {
				throw new IllegalArgumentException("Not found");
			}
		} catch(Exception e) {
			final String msg = "Error get funcionario";
			FuncionarioService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	@Transactional(rollbackOn = Exception.class)
	public FuncionarioDTO update(FuncionarioDTO funcionarioDTO) {
		try {
			funcionarioDTO.setAtestadoSaude(Utils.fromStringToByte(funcionarioDTO.getAtestadoSaudeBase64()));
			Funcionario funcionario = this.mapper.map(funcionarioDTO, Funcionario.class);
			
			Optional<Cargo> optionalCargo = cargoRepository.findById(funcionarioDTO.getCargo().getId());
			if(optionalCargo.isPresent()) {
				Cargo cargo = optionalCargo.get();
				funcionario.setCargo(cargo);
			}
			
			funcionario = this.funcionarioRepository.save(funcionario);
			funcionario.getFuncionarioAtividadeEPIs().stream().forEach(at -> funcionarioAtividadeEPIRepository.delete(at));
			final Funcionario fnc = funcionario;
			funcionarioDTO.getStividadesEpis().stream().forEach(at -> this.createAtividadeEPI(fnc, at.getAtividadeId(), at.getEpiId(), at.getNumeroCA()));
			
			funcionario = this.funcionarioRepository.findById(funcionario.getId()).get();
			return this.mapper.map(funcionario, FuncionarioDTO.class);
		} catch(Exception e) {
			final String msg = "Error update funcionario";
			FuncionarioService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public boolean delete(long id) {
		try {
			if(Objects.isNull(id) || id < 1L) {
				throw new IllegalArgumentException("Invalid identifier");
			}
			Optional<Funcionario> optionalFuncionario = this.funcionarioRepository.findById(id);
			if(optionalFuncionario.isPresent()) {
				Funcionario funcionario = optionalFuncionario.get();
				List<FuncionarioAtividadeEPI> funcionarioAtividadeEpis = this.funcionarioAtividadeEPIRepository.findByFuncionarioId(funcionario.getId());
				for(FuncionarioAtividadeEPI funcionarioAtividadeEpi : funcionarioAtividadeEpis) {
					this.funcionarioAtividadeEPIRepository.delete(funcionarioAtividadeEpi);
				}
				this.funcionarioRepository.delete(funcionario);
				return true;
			} else {
				throw new IllegalArgumentException("Not found");
			}
		} catch(Exception e) {
			final String msg = "Error delete funcionario";
			FuncionarioService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
}
