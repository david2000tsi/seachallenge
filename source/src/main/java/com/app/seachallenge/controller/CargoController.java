package com.app.seachallenge.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.dto.CargoDTO;
import com.app.seachallenge.service.CargoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cargo")
@RequiredArgsConstructor
public class CargoController {
	
	private final CargoService service;

	@GetMapping(value = "get")
	public ResponseEntity<List<CargoDTO>> getAll() {
		List<CargoDTO> cargos = this.service.getAll();
		return ResponseEntity.ok(cargos);
	}
}
