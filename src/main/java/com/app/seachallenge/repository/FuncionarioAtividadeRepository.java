package com.app.seachallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.FuncionarioAtividade;

@Repository
public interface FuncionarioAtividadeRepository extends CrudRepository<FuncionarioAtividade, Long>{

}
