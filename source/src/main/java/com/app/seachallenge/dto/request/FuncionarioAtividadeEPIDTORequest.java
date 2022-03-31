package com.app.seachallenge.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FuncionarioAtividadeEPIDTORequest {
	@NotNull
	private Long funcionario;
	@NotNull
	private Long atividade;
	@NotNull
	private Long epi;
	@NotNull
	private Long numeroCA;
}
