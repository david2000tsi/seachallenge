package com.app.seachallenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
	
	private final TestService service;
	
	@GetMapping(value = "/create-data")
	public ResponseEntity<String> createBasicData() {
		this.service.createData();
		return ResponseEntity.ok("OK");
	}
}
