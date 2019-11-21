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

@Entity
@Table(name = "tb_funcionarioatividade")
@SequenceGenerator(name = "sq_funcionarioatividade", initialValue = 1)
public class FuncionarioAtividade implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_funcionarioatividade")
	private long id;
	
	@ManyToOne
	@JoinColumn
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn
	private Atividade atividade;

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
}
