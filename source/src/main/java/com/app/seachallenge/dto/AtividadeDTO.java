package com.app.seachallenge.dto;

import com.app.seachallenge.model.Atividade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtividadeDTO {
	private long id;
	private String nome;
	
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
}
