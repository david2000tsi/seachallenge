package com.app.seachallenge.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.seachallenge.dto.FuncionarioDTO;
import com.app.seachallenge.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("funcionario")
@RequiredArgsConstructor
public class FuncionarioController {
	
	private final FuncionarioService service;
	
	@PostMapping(value = "save")
	public ResponseEntity<FuncionarioDTO> create(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
		FuncionarioDTO funcionario = this.service.create(funcionarioDTO);
		return ResponseEntity.ok(funcionario);
	}
	
	@GetMapping(value = "get")
	public ResponseEntity<List<FuncionarioDTO>> getAll(@RequestParam(required = false) Boolean ativo) {
		List<FuncionarioDTO> funcionarios = this.service.getAll(ativo);
		return ResponseEntity.ok(funcionarios);
	}
	
	@GetMapping(value = "get/{id}")
	public ResponseEntity<FuncionarioDTO> getOne(@PathVariable("id") long id) {
		 FuncionarioDTO funcionario = this.service.getOne(id);
		 return ResponseEntity.ok(funcionario);
	}
	
	@PutMapping(value = "update")
	public ResponseEntity<FuncionarioDTO> update(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
		FuncionarioDTO funcionario = this.service.update(funcionarioDTO);
		return ResponseEntity.ok(funcionario);
	}
	
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		boolean delete = this.service.delete(id);
		return ResponseEntity.ok(delete);
	}
}
