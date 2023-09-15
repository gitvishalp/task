package com.cqs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cqs.entity.Designition;

public interface DesignitionRepository extends JpaRepository<Designition, String> {

	Optional<Designition> findByCode(String code);
}
