package com.app.seachallenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.seachallenge.dto.AtividadeDTO;
import com.app.seachallenge.model.Atividade;
import com.app.seachallenge.repository.AtividadeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtividadeService {
	
	private final AtividadeRepository atividadeRepository;
	private final MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();

	public List<AtividadeDTO> getAll() {
		try {
			List<Atividade> atividades = this.atividadeRepository.findAll();
			return this.mapper.mapAsList(atividades, AtividadeDTO.class);
		} catch(Exception e) {
			final String msg = "Error get atividades";
			AtividadeService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public boolean exists(String nomeAtividade) {
		try {
			Optional<Atividade> atividade = this.atividadeRepository.findByNome(nomeAtividade);
			return atividade.isPresent();
		} catch(Exception e) {
			final String msg = "Error check atividade";
			AtividadeService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public AtividadeDTO create(String nomeAtividade) {
		try {
			if(!this.exists(nomeAtividade)) {
				Atividade atividade = new Atividade();
				atividade.setNome(nomeAtividade);
				atividade = this.atividadeRepository.save(atividade);
				return this.mapper.map(atividade, AtividadeDTO.class);
			} else {
				throw new IllegalArgumentException("Atividade already exists.");
			}
		} catch(IllegalArgumentException e) {
			AtividadeService.log.error(e.getMessage());
			throw e;
		} catch(Exception e) {
			final String msg = "Error create atividade";
			AtividadeService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
}
