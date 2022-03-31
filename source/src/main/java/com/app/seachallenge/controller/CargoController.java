package com.app.seachallenge.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.model.Cargo;
import com.app.seachallenge.repository.CargoRepository;

@RestController
public class CargoController extends UtilsController {
	
	@Autowired
	private CargoRepository cargoRepository;

	@RequestMapping(value = "/cargo/get/all")
	public String getCargos() {
		String result = "";
		
		try {
			Iterable<Cargo> cargos = cargoRepository.findAll();
			ArrayList<JSONObject> jsonCargos = new ArrayList<JSONObject>();
			for(Cargo cargo : cargos) {
				JSONObject jsonCargo = new JSONObject();
				jsonCargo.put("id", cargo.getId());
				jsonCargo.put("nome", cargo.getNome());
				jsonCargos.add(jsonCargo);
			}
			JSONObject response = new JSONObject();
			response.put("cargos", jsonCargos);
			result = response.toString();
		} catch(Exception e) {
			e.printStackTrace();
			result = getMsgJson(-1, "GET_CARGOS_ERR");
		}
		
		return result;
	}
	
	public boolean existsCargo(String nomeCargo) {
		boolean result = false;
		try {
			Optional<Cargo> cargo = cargoRepository.findByNome(nomeCargo);
			result = cargo.isPresent();
		} catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	public void createCargo(String nomeCargo) {
		try {
			if(existsCargo(nomeCargo) == false) {
				Cargo cargo = new Cargo();
				cargo.setNome(nomeCargo);
				
				cargoRepository.save(cargo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
