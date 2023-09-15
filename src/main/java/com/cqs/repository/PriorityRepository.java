package com.cqs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cqs.entity.Priority;

public interface PriorityRepository extends JpaRepository<Priority, String> {

	Optional<Priority> findByCode(String code);
}
