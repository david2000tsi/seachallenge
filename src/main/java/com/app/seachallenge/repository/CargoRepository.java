package com.app.seachallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long>{

}
