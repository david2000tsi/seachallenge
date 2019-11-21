package com.app.seachallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.AtividadeEPI;

@Repository
public interface AtividadeEPIRepository extends CrudRepository<AtividadeEPI, Long>{

}
