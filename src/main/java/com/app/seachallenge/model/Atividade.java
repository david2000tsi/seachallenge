package com.app.seachallenge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_atividade")
@SequenceGenerator(name = "sq_atividade", initialValue = 1)
public class Atividade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_atividade")
	private long id;
	
	private String nome;
	
	@OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL)
	private List<FuncionarioAtividade> funcionarioAtividades;
	
	@OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL)
	private List<AtividadeEPI> atividadeEpis;

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

	public List<FuncionarioAtividade> getFuncionarioAtividades() {
		return funcionarioAtividades;
	}

	public void setFuncionarioAtividades(List<FuncionarioAtividade> funcionarioAtividades) {
		this.funcionarioAtividades = funcionarioAtividades;
	}
	
	public List<AtividadeEPI> getAtividadeEpis() {
		return atividadeEpis;
	}

	public void setAtividadeEpis(List<AtividadeEPI> atividadeEpis) {
		this.atividadeEpis = atividadeEpis;
	}
}
