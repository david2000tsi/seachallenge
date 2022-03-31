package com.app.seachallenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.seachallenge.dto.EPIDTO;
import com.app.seachallenge.model.EPI;
import com.app.seachallenge.repository.EPIRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class EPIService {

	private final EPIRepository epiRepository;
	private final MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();
	
	public List<EPIDTO> getAll() {
		try {
			List<EPI> epis = this.epiRepository.findAll();
			return this.mapper.mapAsList(epis, EPIDTO.class);
		} catch(Exception e) {
			final String msg = "Error get epis";
			EPIService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public boolean exists(String nomeEPI) {
		try {
			Optional<EPI> epi = this.epiRepository.findByNome(nomeEPI);
			return epi.isPresent();
		} catch(Exception e) {
			final String msg = "Error check epi";
			EPIService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public EPIDTO create(String nomeEPI) {
		try {
			if(exists(nomeEPI) == false) {
				EPI epi = new EPI();
				epi.setNome(nomeEPI);
				epi = this.epiRepository.save(epi);
				return this.mapper.map(epi, EPIDTO.class);
			} else {
				throw new IllegalArgumentException("Epi already exists.");
			}
		} catch(IllegalArgumentException e) {
			EPIService.log.error(e.getMessage());
			throw e;
		} catch(Exception e) {
			final String msg = "Error create epi";
			EPIService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
}
