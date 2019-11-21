package com.app.seachallenge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.Nullable;

@Entity
@Table(name = "tb_funcionario")
@SequenceGenerator(name = "sq_funcionario", initialValue = 1)
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_funcionario")
	private long id;
	private String nome;
	private String cpf;
	private String rg;
	private String sexo;
	private Date dataNascimento;
	private boolean ativo;
	
	@Nullable
	private byte[] atestadoSaude;
	
	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
	private List<FuncionarioAtividade> funcionarioAtividades;
	
	@ManyToOne
	@JoinColumn
	private Cargo cargo;

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

	public List<FuncionarioAtividade> getFuncionarioAtividades() {
		return funcionarioAtividades;
	}

	public void setFuncionarioAtividades(List<FuncionarioAtividade> funcionarioAtividades) {
		this.funcionarioAtividades = funcionarioAtividades;
	}
}
