package com.cqs.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cqs.entity.AdminNotifications;
import com.cqs.entity.Employee;
import com.cqs.entity.Project;
import com.cqs.entity.Task;
import com.cqs.requestdto.AddDesignationRequest;
import com.cqs.requestdto.AddEmployeeRequest;
import com.cqs.requestdto.AddProjectRequest;
import com.cqs.requestdto.AddRoleRequest;
import com.cqs.requestdto.AddTaskRequest;
import com.cqs.requestdto.AdminLoginRequest;
import com.cqs.requestdto.UpdateTaskRequest;
import com.cqs.responsedto.LoginResponse;
import com.cqs.responsedto.Response;
import com.cqs.service.AdminService;
import com.cqs.util.JWTTokenUtil;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")
public class AdminController implements Serializable {
	
	private static final long serialVersionUID = 2299780644527779358L;
	
	private final AdminService adminService;
	
	
	@PostMapping("/Login")
	Response<LoginResponse> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
		return adminService.adminLogin(request);
	}
	@PostMapping("/AddEmployee")
	Response<String> addEmployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@Valid @RequestBody AddEmployeeRequest request) throws UnsupportedEncodingException, MessagingException{
		return adminService.addEmployee(JWTTokenUtil.getUserIdFromToken(token.substring(7)), request);
	}
    @PostMapping("/AddRole")	
    Response<String> addRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@Valid @RequestBody AddRoleRequest request){
		return adminService.addRole(JWTTokenUtil.getUserIdFromToken(token.substring(7)), request);
	}
    @PostMapping("/AddDesignition")
    Response<String> addDesignition(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@Valid @RequestBody AddDesignationRequest request){
		return adminService.addDesignition(JWTTokenUtil.getUserIdFromToken(token.substring(7)), request);
	}
    @PostMapping("/AddProject")
    Response<String> addProject(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@Valid @RequestBody AddProjectRequest request){
		return adminService.addProject(JWTTokenUtil.getUserIdFromToken(token.substring(7)), request);
	}
    @PostMapping("/AddTask")
    Response<String> addTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@Valid @RequestBody AddTaskRequest request){
		return adminService.addTask(JWTTokenUtil.getUserIdFromToken(token.substring(7)), request);
	}
    @PutMapping("/UpdateTask/{TaskId}")
    Response<String> updateTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("TaskId")String taskId,@Valid @RequestBody UpdateTaskRequest request){
		return adminService.updateTask(JWTTokenUtil.getUserIdFromToken(token.substring(7)),taskId, request);
	}
    @DeleteMapping("/Project/{ProjectId}")
    Response<String> deleteProject(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("ProjectId")String projectId){
		return adminService.deleteProject(JWTTokenUtil.getUserIdFromToken(token.substring(7)),projectId);
	}
    @DeleteMapping("/Tasks-by-project/{ProjectId}")
    Response<String> deleteTasksByProjectId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("ProjectId")String projectId){
		return adminService.deleteTaskByProjectId(JWTTokenUtil.getUserIdFromToken(token.substring(7)),projectId);
	}
    @DeleteMapping("/Tasks-by-employee/{EmployeeId}")
    Response<String> deleteTasksByEmployeeId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("EmployeeId")String employeeId){
		return adminService.deleteTaskByEmployeeId(JWTTokenUtil.getUserIdFromToken(token.substring(7)),employeeId);
	}
    @DeleteMapping("/Task/{TaskId}")
    Response<String> deleteTasksById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("TaskId")String taskId){
		return adminService.deleteTask(JWTTokenUtil.getUserIdFromToken(token.substring(7)),taskId);
	}
    @DeleteMapping("/employee/{empId}")
    Response<String> deleteEmployeeById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("empId")String empId){
		return adminService.deleteEmployee(JWTTokenUtil.getUserIdFromToken(token.substring(7)),empId);
	}
    @GetMapping("/employee")
    Response<List<Employee>> getAllEmployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
    	return adminService.getAllEmployees(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
    }
    @GetMapping("/employee/{EmployeeId}")
    Response<Employee> getAllEmployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("EmployeeId")String empId){
    	return adminService.getEmployeeById(JWTTokenUtil.getUserIdFromToken(token.substring(7)), empId);
    }
    @GetMapping("/project")
    Response<List<Project>> getAllProject(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
    	return adminService.getAllProject(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
    }
    @GetMapping("/project/{ProjectId}")
    Response<Project> getProjectById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("ProjectId") String projectid){
    	return adminService.getProjectById(JWTTokenUtil.getUserIdFromToken(token.substring(7)),projectid);
    }
    @GetMapping("/task")
    Response<List<Task>> findAllTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
    	return adminService.getAllTask(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
    }
    @GetMapping("/task/{TaskId}")
    Response<Task> findAllTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("TaskId")String taskId){
    	return adminService.getTaskById(JWTTokenUtil.getUserIdFromToken(token.substring(7)),taskId);
    }
    @GetMapping("/task-by-project/{ProjectId}")
    Response<List<Task>> findTaskByProjectId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("ProjectId")String projectId){
    	return adminService.getAllTasksByProjectId(JWTTokenUtil.getUserIdFromToken(token.substring(7)),projectId);
    }
    @GetMapping("/task-by-employee/{EmployeeId}")
    Response<List<Task>> findTaskByEmployeeId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("EmployeeId")String employeeId){
    	return adminService.getAllTasksByEmployeeId(JWTTokenUtil.getUserIdFromToken(token.substring(7)),employeeId);
    }
    @GetMapping("/CountProject/{Status}")
    Response<Integer> countProject(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("Status")String status){
    	return adminService.countProjectByStatus(JWTTokenUtil.getUserIdFromToken(token.substring(7)),status);
    }
    @GetMapping("/CountTask/{Status}")
    Response<Integer> countTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("Status")String status){
    	return adminService.countTaskByStatus(JWTTokenUtil.getUserIdFromToken(token.substring(7)),status);
    }
    @GetMapping("/notification")
    Response<List<AdminNotifications>> getNotifications(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
    	return adminService.getAllNotifications(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
    }
    @DeleteMapping("/notification/{NotificationId}")
    Response<String> deleteNotification(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("NotificationId") String notificationId){
    	return adminService.deleteNotificationById(JWTTokenUtil.getUserIdFromToken(token.substring(7)),notificationId);
    }
}
