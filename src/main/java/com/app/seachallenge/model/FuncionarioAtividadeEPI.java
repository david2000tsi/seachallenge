package com.app.seachallenge.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.Nullable;

@Entity
@Table(name = "tb_funcionarioatividadeepi")
@SequenceGenerator(name = "sq_funcionarioatividadeepi", initialValue = 1, allocationSize = 1)
public class FuncionarioAtividadeEPI implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_funcionarioatividadeepi")
	private long id;
	
	@ManyToOne
	@JoinColumn
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn
	private Atividade atividade;
	
	@ManyToOne
	@JoinColumn
	@Nullable
	private EPI epi;
	
	@Nullable
	private long numeroCA;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public EPI getEpi() {
		return epi;
	}

	public void setEpi(EPI epi) {
		this.epi = epi;
	}

	public long getNumeroCA() {
		return numeroCA;
	}

	public void setNumeroCA(long numeroCA) {
		this.numeroCA = numeroCA;
	}
}
