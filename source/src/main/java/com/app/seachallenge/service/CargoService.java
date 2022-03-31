package com.app.seachallenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.seachallenge.dto.CargoDTO;
import com.app.seachallenge.model.Cargo;
import com.app.seachallenge.repository.CargoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class CargoService {
	
	private final CargoRepository cargoRepository;
	private final MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();

	public List<CargoDTO> getAll() {
		try {
			List<Cargo> cargos = this.cargoRepository.findAll();
			return this.mapper.mapAsList(cargos, CargoDTO.class);
		} catch(Exception e) {
			final String msg = "Error get cargos";
			CargoService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public boolean exists(String nomeCargo) {
		try {
			Optional<Cargo> cargo = this.cargoRepository.findByNome(nomeCargo);
			return cargo.isPresent();
		} catch(Exception e) {
			final String msg = "Error check cargo";
			CargoService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	public CargoDTO create(String nomeCargo) {
		try {
			if(exists(nomeCargo) == false) {
				Cargo cargo = new Cargo();
				cargo.setNome(nomeCargo);
				cargo = this.cargoRepository.save(cargo);
				return this.mapper.map(cargo, CargoDTO.class);
			} else {
				throw new IllegalArgumentException("Cargo already exists.");
			}
		} catch(IllegalArgumentException e) {
			CargoService.log.error(e.getMessage());
			throw e;
		} catch(Exception e) {
			final String msg = "Error create cargo";
			CargoService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
}
