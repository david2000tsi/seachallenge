package com.app.seachallenge.controller;

import java.util.ArrayList;

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

	@RequestMapping(value = "/getcargos")
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
			result = getErrorJson(-1, "GET_CARGOS_ERR");
		}
		
		return result;
	}
}
