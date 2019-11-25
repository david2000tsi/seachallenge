package com.app.seachallenge.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class TestController extends UtilsController {
	
	@Autowired
	private CargoController cargoController;
	
	@Autowired
	private AtividadeController atividadeController;
	
	@Autowired
	private EPIController epiController;
	
	@RequestMapping(value = "/create_basic_data", method = RequestMethod.GET)
	public String createBasicData() {
		String result = "";
		try {
			cargoController.createCargo("Gerente");
			cargoController.createCargo("Operário");
			cargoController.createCargo("Auxiliar");
			
			atividadeController.createAtividade("Construir");
			atividadeController.createAtividade("Dirigir");
			atividadeController.createAtividade("Monitorar");
			
			epiController.createEPI("Capacete");
			epiController.createEPI("Mascara");
			epiController.createEPI("Viseira");
			epiController.createEPI("Luva");
			epiController.createEPI("Botina");
			
			result = getMsgJson(0, "CREATE_BASIC_DATA_SUCCESS");
		} catch(Exception e) {
			e.printStackTrace();
			result = getMsgJson(-1, "CREATE_BASIC_DATA_ERR");
		}

		return result;
	}
}
