package com.app.seachallenge.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_funcionario")
@SequenceGenerator(name = "sq_funcionario", initialValue = 1, allocationSize = 1)
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_funcionario")
	@Column(name = "id")
	private long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@Column(name = "ativo")
	private boolean ativo;
	
	@Nullable
	@Column(name = "atestado_saude")
	private byte[] atestadoSaude;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cargo")
	private Cargo cargo;
	
	@OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER)
	private List<FuncionarioAtividadeEPI> funcionarioAtividadeEPIs;
}
