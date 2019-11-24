package com.app.seachallenge.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.model.EPI;
import com.app.seachallenge.repository.EPIRepository;

@RestController
public class EPIController extends UtilsController {

	@Autowired
	private EPIRepository epiRepository;
	
	@RequestMapping(value = "/epi/get/all")
	public String getEpiss() {
		String result = "";
		
		try {
			Iterable<EPI> epis = epiRepository.findAll();
			ArrayList<JSONObject> jsonEPIs = new ArrayList<JSONObject>();
			for(EPI epi : epis) {
				JSONObject jsonEPI = new JSONObject();
				jsonEPI.put("id", epi.getId());
				jsonEPI.put("nome", epi.getNome());
				jsonEPIs.add(jsonEPI);
			}
			JSONObject response = new JSONObject();
			response.put("epis", jsonEPIs);
			result = response.toString();
		} catch(Exception e) {
			e.printStackTrace();
			result = getMsgJson(-1, "GET_EPIS_ERR");
		}
		
		return result;
	}
	
	public boolean existsEPI(String nomeEPI) {
		boolean result = false;
		try {
			Optional<EPI> epi = epiRepository.findByNome(nomeEPI);
			result = epi.isPresent();
		} catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	public void createEPI(String nomeEPI) {
		try {
			if(existsEPI(nomeEPI) == false) {
				EPI epi = new EPI();
				epi.setNome(nomeEPI);
				
				epiRepository.save(epi);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
