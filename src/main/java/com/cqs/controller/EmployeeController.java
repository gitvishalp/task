package com.cqs.controller;

import java.io.Serializable;
import java.util.List;

import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cqs.entity.Employee;
import com.cqs.entity.Project;
import com.cqs.entity.Task;
import com.cqs.requestdto.ChangePasswordRequest;
import com.cqs.requestdto.EmployeeLogin;
import com.cqs.responsedto.EmployeeLoginResponse;
import com.cqs.responsedto.Response;
import com.cqs.service.EmployeeService;
import com.cqs.util.JWTTokenUtil;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employee")
public class EmployeeController implements Serializable {
	private static final long serialVersionUID = 8511621050280278454L;
    
	private final EmployeeService employeeService;
	
	@PostMapping("/login")
	Response<EmployeeLoginResponse> employeeLogin(@Valid @RequestBody EmployeeLogin request) {
		return employeeService.login(request);
	}
	@PutMapping("/change-password")
	Response<String> changePassowrd(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestBody ChangePasswordRequest request){
		return employeeService.changePassword(JWTTokenUtil.getUserIdFromToken(token.substring(7)), request);
	}
	@GetMapping("")
	Response<Employee> getemployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		return employeeService.getEmployeeById(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
	}
	@GetMapping("/task")	
	Response<List<Task>> getTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		return employeeService.getTaskByEmployeeId(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
	}
	@GetMapping("/project")
	Response<List<Project>> getProjects(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		return employeeService.myProject(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
	}
	@GetMapping("/task/{status}")
	Response<Integer> getTaskByStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("status")String status){
		return employeeService.getTaskByStatus(JWTTokenUtil.getUserIdFromToken(token.substring(7)), status);
	}
	@GetMapping("/project-count")
	Response<Integer> getprojectCount(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		return employeeService.getProjectCount(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
	}
}
