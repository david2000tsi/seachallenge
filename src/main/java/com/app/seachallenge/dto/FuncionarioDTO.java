package com.app.seachallenge.dto;

import java.util.Date;

import com.app.seachallenge.model.Cargo;
import com.app.seachallenge.model.Funcionario;

public class FuncionarioDTO {
	private long id;
	private String nome;
	private String cpf;
	private String rg;
	private String sexo;
	private Date dataNascimento;
	private boolean ativo;
	private byte[] atestadoSaude;
	private Cargo cargo;
	
	public FuncionarioDTO() {
	}
	
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public byte[] getAtestadoSaude() {
		return atestadoSaude;
	}
	public void setAtestadoSaude(byte[] atestadoSaude) {
		this.atestadoSaude = atestadoSaude;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
}
