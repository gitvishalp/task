package com.cqs.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

import jakarta.mail.MessagingException;

public interface AdminService extends Serializable {
	
	Response<LoginResponse> adminLogin(AdminLoginRequest request);
	Response<String> addRole(String adminId, AddRoleRequest request);
	Response<String> addDesignition(String adminId, AddDesignationRequest request);
	Response<String> addEmployee(String adminId,AddEmployeeRequest request) throws UnsupportedEncodingException, MessagingException;
	Response<String> addProject(String adminId, AddProjectRequest request);
	Response<String> addTask(String adminId, AddTaskRequest request)throws UnsupportedEncodingException, MessagingException;
	Response<String> updateTask(String adminId,String taskId, UpdateTaskRequest request)throws UnsupportedEncodingException, MessagingException;
	Response<String> deleteProject(String adminId,String projectId);
	Response<String> deleteTaskByProjectId(String adminId, String projectId);
	Response<String> deleteTaskByEmployeeId(String adminId, String employeeId);
	Response<String> deleteTask(String adminId,String taskId);
	Response<List<Employee>> getAllEmployees(String adminId);
	Response<Employee> getEmployeeById(String adminId, String employeeId);
	Response<List<Project>> getAllProject(String adminId);
	Response<Project> getProjectById(String adminId, String ProjectId);
	Response<List<Task>> getAllTask(String adminId);
	Response<Task> getTaskById(String adminId, String taskId);
	Response<List<Task>> getAllTasksByProjectId(String adminId, String projectId);
	Response<List<Task>> getAllTasksByEmployeeId(String adminId,String employeeId);
	Response<Integer> countProjectByStatus(String adminId, String Status);  
	Response<Integer> countTaskByStatus(String adminId, String Status);
	Response<String> deleteEmployee(String adminId, String empId);
	Response<List<AdminNotifications>> getAllNotifications(String adminId);
	Response<String> deleteNotificationById(String adminId, String notificationId);
}
