package com.app.seachallenge.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
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
@Table(name = "tb_cargo")
@SequenceGenerator(name = "sq_cargo", initialValue = 1, allocationSize = 1)
public class Cargo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_cargo")
	@Column(name = "id")
	private long id;
	
	@Column(name = "nome")
	private String nome;
	
	@OneToMany(mappedBy = "cargo")
	private List<Funcionario> funcionarios;
}
