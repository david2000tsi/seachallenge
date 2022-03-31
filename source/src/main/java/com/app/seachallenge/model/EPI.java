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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_epi")
@SequenceGenerator(name = "sq_epi", initialValue = 1, allocationSize = 1)
public class EPI implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_epi")
	private long id;
	
	private String nome;
	
	@OneToMany(mappedBy = "epi", cascade = CascadeType.ALL)
	private List<FuncionarioAtividadeEPI> funcionarioAtividadeEpis;
}
