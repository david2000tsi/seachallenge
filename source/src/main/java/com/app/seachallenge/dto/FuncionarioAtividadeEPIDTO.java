package com.app.seachallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FuncionarioAtividadeEPIDTO {
	private long id;
	@JsonIgnore
	private FuncionarioDTO funcionario;
	private AtividadeDTO atividade;
	private EPIDTO epi;
	private long numeroCA;
}
