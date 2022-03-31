package com.app.seachallenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.seachallenge.model.EPI;

@Repository
public interface EPIRepository extends JpaRepository<EPI, Long> {

	public Optional<EPI> findByNome(String nome);
}
