package com.app.seachallenge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(mappedBy = "atividades")
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
	
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "tb_atividadeepi",
			joinColumns = @JoinColumn(name = "id_atividade"),
			inverseJoinColumns = @JoinColumn(name = "id_epi"))
	private List<EPI> epis = new ArrayList<EPI>();

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

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<EPI> getEpis() {
		return epis;
	}

	public void setEpis(List<EPI> epis) {
		this.epis = epis;
	}
	
	public void addEPI(EPI epi) {
		epis.add(epi);
		epi.getAtividades().add(this);
	}
	
	public void removeEPI(EPI epi) {
		epis.remove(epi);
		epi.getAtividades().remove(this);
	}
}
