package com.app.seachallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.FuncionarioAtividadeEPI;

@Repository
public interface FuncionarioAtividadeEPIRepository extends CrudRepository<FuncionarioAtividadeEPI, Long>{

	public Iterable<FuncionarioAtividadeEPI> findByFuncionarioId(long id);
	
	public Iterable<FuncionarioAtividadeEPI> findByFuncionarioIdAndAtividadeIdAndEpiIdAndNumeroCA(long funcionarioId, long atividadeId, long epiId, long numeroCA);
}
