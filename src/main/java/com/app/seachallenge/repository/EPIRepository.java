package com.app.seachallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.EPI;

@Repository
public interface EPIRepository extends CrudRepository<EPI, Long>{

}
