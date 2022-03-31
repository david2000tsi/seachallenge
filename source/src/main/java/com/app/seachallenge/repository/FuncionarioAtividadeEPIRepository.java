package com.app.seachallenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.FuncionarioAtividadeEPI;

@Repository
public interface FuncionarioAtividadeEPIRepository extends JpaRepository<FuncionarioAtividadeEPI, Long> {

	public List<FuncionarioAtividadeEPI> findByFuncionarioId(long id);
	
	public List<FuncionarioAtividadeEPI> findByFuncionarioIdAndAtividadeIdAndEpiIdAndNumeroCA(long funcionarioId, long atividadeId, long epiId, long numeroCA);
}
