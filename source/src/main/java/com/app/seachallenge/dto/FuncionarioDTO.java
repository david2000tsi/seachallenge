package com.app.seachallenge.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FuncionarioDTO {
	private long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String cpf;
	@NotBlank
	private String rg;
	@NotBlank
	private String sexo;
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNascimento;
	@NotNull
	private boolean ativo;
	private byte[] atestadoSaude;
	@NotNull
	private CargoDTO cargo;
	private List<FuncionarioAtividadeEPIDTO> funcionarioAtividadeEPIs;
}
