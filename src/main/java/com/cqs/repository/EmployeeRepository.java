package com.cqs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cqs.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,String>{
	
	Optional<Employee> findByEmail(String email);
    
	
	@Query("SELECT e FROM Employee e")
	List<Employee> getAllEmployees();
}
