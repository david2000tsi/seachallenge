package com.app.seachallenge.controller;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.model.Atividade;
import com.app.seachallenge.repository.AtividadeRepository;

@RestController
public class AtividadeController extends UtilsController {
	
	@Autowired
	private AtividadeRepository atividadeRepository;

	@RequestMapping(value = "/getatividades")
	public String getAtividades() {
		String result = "";
		
		try {
			Iterable<Atividade> atividades = atividadeRepository.findAll();
			ArrayList<JSONObject> jsonAtividades = new ArrayList<JSONObject>();
			for(Atividade atividade : atividades) {
				JSONObject jsonAtividade = new JSONObject();
				jsonAtividade.put("id", atividade.getId());
				jsonAtividade.put("nome", atividade.getNome());
				jsonAtividades.add(jsonAtividade);
			}
			JSONObject response = new JSONObject();
			response.put("atividades", jsonAtividades);
			result = response.toString();
		} catch(Exception e) {
			e.printStackTrace();
			result = getErrorJson(-1, "GET_ATIVIDADES_ERR");
		}
		
		return result;
	}
}
