package com.cqs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cqs.entity.Task;

public interface TaskRepository extends JpaRepository<Task, String>{

	@Query("SELECT t FROM Task t")
	List<Task> findAllTask();
	
	@Query("SELECT t FROM Task t WHERE t.project.id = ?1 ")
	List<Task> findTaskByProjectId(String projectId);
	
	@Query("SELECT t FROM Task t WHERE t.assignee.id = ?1")
	List<Task> findTaskByEmployeeId(String employeeId);
	
	@Query("SELECT COUNT(id) FROM Task t WHERE t.status = ?1" )
	int countTaskByStatus(String status);
	
	@Modifying
	@Query("DELETE FROM Task t WHERE t.project.id = ?1 ")
	int deleteByProjectId(String projectId);
	
	
	@Modifying
	@Query("DELETE FROM Task t WHERE t.assignee.id = ?1 ")
	int deleteByEmployeeId(String employeeId);
	
}
