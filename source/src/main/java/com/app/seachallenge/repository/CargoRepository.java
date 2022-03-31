package com.app.seachallenge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {

	public Optional<Cargo> findByNome(String nome);
}
