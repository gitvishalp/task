package com.cqs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cqs.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {
	
	
	@Query("SELECT p FROM Project p ")
	List<Project> getAllProject();

	@Query("SELECT COUNT(id) FROM Project p WHERE p.status = ?1 ")
	int countProject(String status);
	
}
