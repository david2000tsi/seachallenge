package com.app.seachallenge.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.dto.AtividadeDTO;
import com.app.seachallenge.service.AtividadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("atividade")
@RequiredArgsConstructor
public class AtividadeController {
	
	private final AtividadeService service;

	@GetMapping(value = "get")
	public ResponseEntity<List<AtividadeDTO>> getAtividades() {
		List<AtividadeDTO> atividades = this.service.getAll();
		return ResponseEntity.ok(atividades);
	}
}
