package com.cqs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cqs.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, String> {

	Optional<Roles> findByCode(String code);
}
