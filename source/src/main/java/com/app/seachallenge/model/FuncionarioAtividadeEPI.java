package com.app.seachallenge.model;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table(name = "tb_funcionario_atividade_epi")
@SequenceGenerator(name = "sq_funcionario_atividade_epi", initialValue = 1, allocationSize = 1)
public class FuncionarioAtividadeEPI implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_funcionario_atividade_epi")
	@Column(name = "id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn(name = "id_atividade")
	private Atividade atividade;
	
	@Nullable
	@ManyToOne
	@JoinColumn(name = "id_epi")
	private EPI epi;
	
	@Nullable
	@Column(name = "numero_ca")
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
