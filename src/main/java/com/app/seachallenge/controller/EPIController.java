package com.app.seachallenge.controller;

import java.util.ArrayList;

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
	
	@RequestMapping(value = "/getepis")
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
			result = getErrorJson(-1, "GET_EPIS_ERR");
		}
		
		return result;
	}
}
