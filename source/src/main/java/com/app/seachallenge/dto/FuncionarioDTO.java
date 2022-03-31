package com.app.seachallenge.dto;

import java.time.LocalDate;

import com.app.seachallenge.model.Cargo;
import com.app.seachallenge.model.Funcionario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FuncionarioDTO {
	private long id;
	private String nome;
	private String cpf;
	private String rg;
	private String sexo;
	private LocalDate dataNascimento;
	private boolean ativo;
	private byte[] atestadoSaude;
	private Cargo cargo;
	
	public FuncionarioDTO(Funcionario funcionario) {
		id = funcionario.getId();
		nome = funcionario.getNome();
		cpf = funcionario.getCpf();
		rg = funcionario.getRg();
		sexo = funcionario.getSexo();
		dataNascimento = funcionario.getDataNascimento();
		ativo = funcionario.isAtivo();
		atestadoSaude = funcionario.getAtestadoSaude();
		cargo = funcionario.getCargo();
	}
	
	public Funcionario build() {
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setRg(rg);
		funcionario.setSexo(sexo);
		funcionario.setDataNascimento(dataNascimento);
		funcionario.setAtivo(ativo);
		funcionario.setAtestadoSaude(atestadoSaude);
		funcionario.setCargo(cargo);
		return funcionario;
	}
	
	public boolean isValidToSave() {
		if(nome == null || nome.length() == 0) {
			return false;
		}
		if(cpf == null || cpf.length() == 0) {
			return false;
		}
		if(rg == null || rg.length() == 0) {
			return false;
		}
		if(sexo == null || sexo.length() == 0 || (sexo.equals("F") == false && sexo.equals("M") == false)) {
			return false;
		}
		if(dataNascimento == null) {
			return false;
		}
		if(cargo == null) {
			return false;
		}
		return true;
	}
}
