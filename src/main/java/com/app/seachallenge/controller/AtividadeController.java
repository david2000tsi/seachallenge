package com.app.seachallenge.controller;

import java.util.ArrayList;
import java.util.Optional;

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

	@RequestMapping(value = "/atividade/get/all")
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
			result = getMsgJson(-1, "GET_ATIVIDADES_ERR");
		}
		
		return result;
	}
	
	public boolean existsAtividade(String nomeAtividade) {
		boolean result = false;
		try {
			Optional<Atividade> atividade = atividadeRepository.findByNome(nomeAtividade);
			result = atividade.isPresent();
		} catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	public void createAtividade(String nomeAtividade) {
		try {
			if(existsAtividade(nomeAtividade) == false) {
				Atividade atividade = new Atividade();
				atividade.setNome(nomeAtividade);
				
				atividadeRepository.save(atividade);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
