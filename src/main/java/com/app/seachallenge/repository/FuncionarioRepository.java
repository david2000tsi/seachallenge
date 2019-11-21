package com.app.seachallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long>{

}
