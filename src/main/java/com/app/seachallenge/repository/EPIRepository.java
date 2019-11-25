package com.app.seachallenge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.EPI;

@Repository
public interface EPIRepository extends CrudRepository<EPI, Long>{

	public Optional<EPI> findByNome(String nome);
}
