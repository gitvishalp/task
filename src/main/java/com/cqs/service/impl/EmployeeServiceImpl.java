package com.cqs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cqs.entity.Admin;
import com.cqs.entity.Designition;
import com.cqs.entity.Employee;
import com.cqs.entity.Project;
import com.cqs.entity.Task;
import com.cqs.repository.DesignitionRepository;
import com.cqs.repository.EmployeeRepository;
import com.cqs.repository.ProjectRepository;
import com.cqs.repository.RolesRepository;
import com.cqs.repository.TaskRepository;
import com.cqs.requestdto.ChangePasswordRequest;
import com.cqs.requestdto.EmployeeLogin;
import com.cqs.responsedto.EmployeeLoginResponse;
import com.cqs.responsedto.Response;
import com.cqs.service.EmployeeService;
import com.cqs.util.JWTTokenUtil;
import com.cqs.util.OtpMailSender;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private static final long serialVersionUID = -4285944097383142099L;
	
	private final EmployeeRepository employeeRepository;
	private final RolesRepository rolesRepository;
	private final DesignitionRepository designitionRepository;
	private final TaskRepository taskRepository;
	private final ProjectRepository projectRepository;
	private final OtpMailSender mailSender;
	private final JWTTokenUtil jwtToken;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Response<EmployeeLoginResponse> login(EmployeeLogin request) {
		if(!StringUtils.hasText(request.getEmail()) || !StringUtils.hasText(request.getPassword())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"invalid credentials");
		}
		Optional<Employee> employee = employeeRepository.findByEmail(request.getEmail());
		if(employee.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"invalid credentials");
		}
		if(passwordEncoder.matches(request.getPassword(), employee.get().getPassword())) {
			EmployeeLoginResponse loginResponse = new EmployeeLoginResponse(jwtToken.generateToken(employee.get().getEmail(), employee.get().getId(), employee.get().getPhoneNumber(), employee.get().getPassword(), employee.get().getRole().getCode()),employee.get().isFirstLogin() ,new Date());
			return new Response<>(HttpStatus.SC_OK,"Success",loginResponse);
		}else {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Invlaid Password");
		}
	}

	@Override
	public Response<String> changePassword(String userId, ChangePasswordRequest request) {
		if(!StringUtils.hasText(request.getNewPass())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"field Required!!");
		}
		Optional<Employee> emp = employeeRepository.findById(userId);
		if(emp.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Not Authorized!!");
		}
		emp.get().setPassword(passwordEncoder.encode(request.getNewPass()).trim());
		if(emp.get().isFirstLogin()) {
			emp.get().setFirstLogin(false);
		}
		employeeRepository.save(emp.get());
		return new Response<>(HttpStatus.SC_OK,"Password Changed");
	}

	@Override
	public Response<Employee> getEmployeeById(String userId) {
		Optional<Employee> emp = employeeRepository.findById(userId);
		if(emp.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Not Authorized!!");
		}
		return new Response<>(HttpStatus.SC_OK,"Success",emp.get());
	}

	@Override
	public Response<List<Task>> getTaskByEmployeeId(String userId) {
		Optional<Employee> emp = employeeRepository.findById(userId);
		if(emp.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Not Authorized!!");
		}
		List<Task> taskList = taskRepository.findTaskByEmployeeId(userId);
		if(taskList.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No task found");
		}
		return new Response<>(HttpStatus.SC_OK,"Success",taskList);
	}

	@Override
	public Response<Integer> getTaskByStatus(String userId, String status) {
		Optional<Employee> emp = employeeRepository.findById(userId);
		if(emp.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Not Authorized!!");
		}
		int count = taskRepository.countTaskByStatusAndEmpId(status, userId);
		Integer c = Integer.valueOf(count);
		return new Response<>(HttpStatus.SC_OK,"Success",c);
	}

	@Override
	public Response<List<Project>> myProject(String userId) {
		Optional<Employee> emp = employeeRepository.findById(userId);
		if(emp.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Not Authorized!!");
		}
		List<Task> task = taskRepository.findTaskByEmployeeId(userId);
		if(task.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No Projects!");
		}
		List<String> projectIds = new ArrayList<String>();
		task.forEach(t->{
			projectIds.add(t.getProject().getId());
		});
		List<Project> projectList = projectRepository.projectByIdList(projectIds);
		if(projectList.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No Projects!");
		}
		return new Response<>(HttpStatus.SC_OK,"Success",projectList);
	}

	@Override
	public Response<Integer> getProjectCount(String userId) {
		Optional<Employee> emp = employeeRepository.findById(userId);
		if(emp.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Not Authorized!!");
		}
		List<Task> task = taskRepository.findTaskByEmployeeId(userId);
		if(task.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No Projects!");
		}
		List<String> projectIds = new ArrayList<String>();
		task.forEach(t->{
			projectIds.add(t.getProject().getId());
		});
        int count = projectRepository.countProjectByEmployee(projectIds);
        Integer c= Integer.valueOf(count);
		return new Response<>(HttpStatus.SC_OK,"Success",c);
	}
}
