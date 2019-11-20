package com.app.seachallenge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.Nullable;

@Entity
@Table(name = "tb_funcionario")
@SequenceGenerator(name = "sq_funcionario", initialValue = 1)
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_funcionario")
	private long id;
	private String nome;
	private String cpf;
	private String rg;
	private String sexo;
	private Date dataNascimento;
	private boolean ativo;
	
	@Nullable
	private byte[] atestadoSaude;
	
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "tb_funcionarioatividade",
			joinColumns = @JoinColumn(name = "id_funcionario"),
			inverseJoinColumns = @JoinColumn(name = "id_atividade"))
	private List<Atividade> atividades = new ArrayList<Atividade>();
	
	@ManyToOne
	@JoinColumn
	private Cargo cargo;

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public byte[] getAtestadoSaude() {
		return atestadoSaude;
	}

	public void setAtestadoSaude(byte[] atestadoSaude) {
		this.atestadoSaude = atestadoSaude;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	
	public void addAtividade(Atividade atividade) {
		atividades.add(atividade);
		atividade.getFuncionarios().add(this);
	}
	
	public void removeAtividade(Atividade atividade) {
		atividades.remove(atividade);
		atividade.getFuncionarios().remove(this);
	}
}
