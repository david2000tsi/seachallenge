package com.app.seachallenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.Atividade;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

	public Optional<Atividade> findByNome(String nome);
}
