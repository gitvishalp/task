package com.cqs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cqs.entity.Project;


public interface ProjectRepository extends JpaRepository<Project, String> {
	
	
	@Query("SELECT p FROM Project p ")
	List<Project> getAllProject();

	@Query("SELECT COUNT(id) FROM Project p WHERE p.status = ?1 ")
	int countProject(String status);
	
	@Query("SELECT p FROM Project p WHERE p.id in :projectIds ")
	List<Project> projectByIdList(@Param("projectIds")List<String> projectIds);
	
	@Query("SELECT COUNT(id) FROM Project WHERE id in :Ids ")
	int countProjectByEmployee(@Param("Ids")List<String> Ids);
}
