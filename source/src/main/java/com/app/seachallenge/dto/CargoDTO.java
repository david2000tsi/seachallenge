package com.app.seachallenge.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CargoDTO {
	@NotNull
	private long id;
	private String nome;
}
