package com.app.seachallenge.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
	
	private final CargoService cargoService;
	private final AtividadeService atividadeService;
	private final EPIService epiService;
	
	@Transactional(rollbackOn = Exception.class)
	public void createData() {
		try {
			this.cargoService.create("Gerente");
			this.cargoService.create("Operário");
			this.cargoService.create("Auxiliar");
			
			this.atividadeService.create("Construir");
			this.atividadeService.create("Dirigir");
			this.atividadeService.create("Monitorar");
			
			this.epiService.create("Capacete");
			this.epiService.create("Mascara");
			this.epiService.create("Viseira");
			this.epiService.create("Luva");
			this.epiService.create("Botina");
		} catch(Exception e) {
			final String msg = "Error create date";
			TestService.log.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
}
