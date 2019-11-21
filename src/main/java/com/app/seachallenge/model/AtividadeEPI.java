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
@Table(name = "tb_atividadeepi")
@SequenceGenerator(name = "sq_atividadeepi", initialValue = 1)
public class AtividadeEPI implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_atividadeepi")
	private long id;
	
	@ManyToOne
	@JoinColumn
	private Atividade atividade;
	
	@ManyToOne
	@JoinColumn
	private EPI epi;
	
	private String numeroCA;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getNumeroCA() {
		return numeroCA;
	}

	public void setNumeroCA(String numeroCA) {
		this.numeroCA = numeroCA;
	}
}
