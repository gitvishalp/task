package com.cqs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cqs.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

	
	Optional<Admin> findByEmail(String email);
	Optional<Admin> findByUserName(String username);
	
}
