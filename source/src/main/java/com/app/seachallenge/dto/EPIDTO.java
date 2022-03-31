package com.app.seachallenge.dto;

import com.app.seachallenge.model.EPI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EPIDTO {
	private long id;
	private String nome;
	
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
}
