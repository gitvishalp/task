package com.cqs.service;

import java.io.Serializable;
import java.util.List;

import com.cqs.entity.Employee;
import com.cqs.entity.Notifications;
import com.cqs.entity.Project;
import com.cqs.entity.Task;
import com.cqs.requestdto.ChangePasswordRequest;
import com.cqs.requestdto.EmployeeLogin;
import com.cqs.requestdto.UpdateTaskByEmployee;
import com.cqs.responsedto.EmployeeLoginResponse;

import com.cqs.responsedto.Response;

public interface EmployeeService extends Serializable {

	Response<EmployeeLoginResponse> login(EmployeeLogin request);
	Response<String> changePassword(String userId,ChangePasswordRequest request);
	Response<Employee> getEmployeeById(String userId);
	Response<List<Task>> getTaskByEmployeeId(String userId);
	Response<List<Project>> myProject(String userId);
	Response<Integer> getTaskByStatus(String userId,String status);
	Response<Integer> getProjectCount(String userId);
	Response<String> updateTaskStatus(String userId, String taskId, UpdateTaskByEmployee request);
	Response<List<Notifications>> getAllNotification(String userId);
	Response<String> deleteNotificationById(String userId, String notificationId);
 }
