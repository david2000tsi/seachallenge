package com.app.seachallenge.model;

import java.io.Serializable;
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
@Table(name = "tb_epi")
@SequenceGenerator(name = "sq_epi", initialValue = 1)
public class EPI implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_epi")
	private long id;
	
	private String nome;
	
	@OneToMany(mappedBy = "epi", cascade = CascadeType.ALL)
	private List<FuncionarioAtividadeEPI> funcionarioAtividadeEpis;

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

	public List<FuncionarioAtividadeEPI> getFuncionarioAtividadeEpis() {
		return funcionarioAtividadeEpis;
	}

	public void setFuncionarioAtividadeEpis(List<FuncionarioAtividadeEPI> funcionarioAtividadeEpis) {
		this.funcionarioAtividadeEpis = funcionarioAtividadeEpis;
	}
}
