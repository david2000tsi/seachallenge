package com.app.seachallenge.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

	public List<Funcionario> findByAtivoTrue();
	
	public List<Funcionario> findByAtivoFalse();
}
