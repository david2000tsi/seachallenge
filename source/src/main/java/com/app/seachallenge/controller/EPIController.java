package com.app.seachallenge.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.dto.EPIDTO;
import com.app.seachallenge.service.EPIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("epi")
@RequiredArgsConstructor
public class EPIController {

	private final EPIService service;
	
	@GetMapping(value = "get")
	public ResponseEntity<List<EPIDTO>> getAll() {
		List<EPIDTO> epis = this.service.getAll();
		return ResponseEntity.ok(epis);
	}
}
