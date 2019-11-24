package com.app.seachallenge.dto;

import com.app.seachallenge.model.EPI;

public class EPIDTO {
	private long id;
	private String nome;
	
	public EPIDTO() {
	}
	
	public EPIDTO(EPI epi) {
		id = epi.getId();
		nome = epi.getNome();
	}
	
	public EPI build() {
		EPI epi = new EPI();
		epi.setId(id);
		epi.setNome(nome);
		return epi;
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
