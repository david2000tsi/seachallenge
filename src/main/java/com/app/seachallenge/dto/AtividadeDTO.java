package com.app.seachallenge.dto;

import com.app.seachallenge.model.Atividade;

public class AtividadeDTO {
	private long id;
	private String nome;
	
	public AtividadeDTO() {
	}
	
	public AtividadeDTO(Atividade atividade) {
		id = atividade.getId();
		nome = atividade.getNome();
	}
	
	public Atividade build() {
		Atividade atividade = new Atividade();
		atividade.setId(id);
		atividade.setNome(nome);
		return atividade;
	}
	
	public boolean isValidToSave() {
		if(nome == null || nome.length() == 0) {
			return false;
		}
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
