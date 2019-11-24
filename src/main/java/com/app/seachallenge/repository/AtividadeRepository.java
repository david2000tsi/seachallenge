package com.app.seachallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.Atividade;

@Repository
public interface AtividadeRepository extends CrudRepository<Atividade, Long>{

}
