package com.cqs.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.cqs.constants.Constants;
import com.cqs.entity.Admin;
import com.cqs.entity.AdminNotifications;
import com.cqs.entity.Designition;
import com.cqs.entity.Employee;
import com.cqs.entity.Notifications;
import com.cqs.entity.Priority;
import com.cqs.entity.Project;
import com.cqs.entity.Roles;
import com.cqs.entity.Task;
import com.cqs.repository.AdminNotificationsRepository;
import com.cqs.repository.AdminRepository;
import com.cqs.repository.DesignitionRepository;
import com.cqs.repository.EmployeeRepository;
import com.cqs.repository.NotificationRepository;
import com.cqs.repository.PriorityRepository;
import com.cqs.repository.ProjectRepository;
import com.cqs.repository.RolesRepository;
import com.cqs.repository.TaskRepository;
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
import com.cqs.util.GenerateOtp;
import com.cqs.util.JWTTokenUtil;
import com.cqs.util.OtpMailSender;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private static final long serialVersionUID = 7028279022330755328L;
   
	private final AdminRepository adminRepository;
	private final RolesRepository rolesRepository;
	private final EmployeeRepository employeeRepository;
	private final DesignitionRepository designitionRepository;
	private final ProjectRepository projectRepository;
	private final PriorityRepository priorityRepository;
	private final TaskRepository taskRepository;
	private final PasswordEncoder passwordEncoder;
	private final JWTTokenUtil jwtToken;
	private final GenerateOtp generateOtp; 
	private final OtpMailSender otpMailSender;
	private final NotificationRepository notificationRepository;
	private final AdminNotificationsRepository adminNotificationsRepository;
	
	@Override
	public Response<LoginResponse> adminLogin(AdminLoginRequest request) {
		Optional<Admin> admin =null;
		if(StringUtils.hasText(request.getEmail())) {
			admin = adminRepository.findByEmail(request.getEmail());
		}else if(StringUtils.hasText(request.getUserName())) {
			admin = adminRepository.findByUserName(request.getUserName());
		}else {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Credentials");
		}
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Credentials");
		}
		if(!StringUtils.hasText(request.getPassword())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Password Required");
		}
		if(passwordEncoder.matches(request.getPassword(), admin.get().getPassword())) {
			LoginResponse loginResponse = new LoginResponse(jwtToken.generateAdminToken(admin.get().getId(), admin.get().getEmail(), admin.get().getUserName(), admin.get().getRole().getCode()), new Date());
		    return new Response<>(HttpStatus.SC_OK,"Success",loginResponse);
		}else {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Invlaid Password");
		}
	}

	@Override
	public Response<String> addEmployee(String adminId, AddEmployeeRequest request) throws UnsupportedEncodingException, MessagingException {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		if(!StringUtils.hasText(request.getEmail())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Email Required!!");
		}
		Optional<Employee> employee = employeeRepository.findByEmail(request.getEmail());
		if(!employee.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Email Already Exists!!");
		}
		if(!StringUtils.hasText(request.getRole())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Role required!!");
		}
		Optional<Roles> role = rolesRepository.findByCode(request.getRole());
		if(role.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Role!!");
		}
		Optional<Designition> designition = designitionRepository.findByCode(request.getDesignition());
		if(designition.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Designition not found!!");
		}
		Employee emp = new Employee();
		emp.setName(request.getName());
		emp.setDesignition(designition.get());
		emp.setEmail(request.getEmail());
		LocalDate dob = request.getDateofbirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		emp.setDateOfBirth(dob);
		emp.setPhoneNumber(request.getPhoneNumber());
		emp.setActive(true);
		emp.setRole(role.get());
		emp.setCreatedAt(new Date());
		String tempPass = generateOtp.generateCode();
		emp.setPassword(passwordEncoder.encode(tempPass).trim());
		emp.setCreatedBy(admin.get().getId());
		emp.setFirstLogin(true);
		employeeRepository.save(emp);
		System.out.println(tempPass);
		//otpMailSender.sendInviteMail(request.getEmail(),request.getName(),tempPass,role.get().getName());
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	public Response<String> addRole(String adminId, AddRoleRequest request) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		if(!StringUtils.hasText(request.getCode())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Code required!!");
		}
		if(!StringUtils.hasText(request.getCode())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Code required!!");
		}
		if(request.getCode().length()>7) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Max 8 charcter allowed for code!");
		}
		Optional<Roles> role = rolesRepository.findByCode(request.getCode());
		if(!role.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Role alreday exists!!");
		}
		Roles newRole = new Roles();
		newRole.setCode(request.getCode().toUpperCase());
		newRole.setName(request.getName());
		if(StringUtils.hasText(request.getDescription())) {
		  newRole.setDescription(request.getDescription());	
		}
		newRole.setActive(true);
		newRole.setCreatedAt(new Date());
		newRole.setCreatedBy(admin.get().getId());
		rolesRepository.save(newRole);
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	public Response<String> addDesignition(String adminId, AddDesignationRequest request) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		if(!StringUtils.hasText(request.getCode())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Code required!!");
		}
		if(!StringUtils.hasText(request.getCode())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Code required!!");
		}
		if(request.getCode().length()>7) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Max 8 charcter allowed for code!");
		}
		Optional<Designition> designition = designitionRepository.findByCode(request.getCode());
		if(!designition.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Designition Already Exists!!");
		}
		Designition desig = new Designition();
		desig.setCode(request.getCode().toUpperCase());
		desig.setName(request.getName());
		if(StringUtils.hasText(request.getDescription())) {
			desig.setDescription(request.getDescription());
		}
		desig.setActive(true);
		desig.setCreatedAt(new Date());
		desig.setCreatedBy(admin.get().getId());
		designitionRepository.save(desig);
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	public Response<String> addProject(String adminId, AddProjectRequest request) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Project project = new Project();
		project.setProjectName(request.getTitle());
		project.setDescription(request.getDescription());
		project.setExpectedCompletion(request.getCompleteDate());
		project.setStatus(Constants.CREATED);
		project.setCreatedAt(new Date());
		Project pr = projectRepository.save(project);
		return new Response<>(HttpStatus.SC_OK,"Success",pr.getId());
	}

	@Override
	public Response<String> addTask(String adminId, AddTaskRequest request) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		if(!StringUtils.hasText(request.getProjectId())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"ProjectId Required!!");
		}
		if(!StringUtils.hasText(request.getAssigneeId())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Please Assign task!!");
		}
		Optional<Project> project = projectRepository.findById(request.getProjectId());
		if(project.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Project Not found!!");
		}
		Optional<Priority> priority = priorityRepository.findByCode(request.getPriority());
		Optional<Employee> employee = employeeRepository.findById(request.getAssigneeId());
		if(employee.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Assignee Not found!!");
		}
		Task task = new Task();
		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setProject(project.get());
		task.setPriority(priority.get());
		task.setAssignee(employee.get());
		task.setStatus(Constants.CREATED);
		task.setActive(true);
		task.setCreatedAt(new Date());
		task.setExpectedCompletionDate(request.getExpectedDate());
		task.setCreatedby(admin.get().getId());
		task.setRemarks("-");
		project.get().setStatus(Constants.INPROGRESS);
		project.get().setActiveTasks(project.get().getActiveTasks()+1);
		employee.get().setActiveTasks(employee.get().getActiveTasks()+1);
		taskRepository.save(task);
		projectRepository.save(project.get());
		employeeRepository.save(employee.get());
		Notifications notification = new Notifications();
		notification.setToId(employee.get().getId());
		notification.setCreatedAt(new Date());
		notification.setTitle(Constants.TASK_ASSIGN);
		notification.setMessage("a new task " + "\"" + request.getTitle() + "\"" + " assigned to you.");
		notification.setType(Constants.NOTI_TYPE_ASSIGNED);
		notificationRepository.save(notification);
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	public Response<String> updateTask(String adminId, String taskId, UpdateTaskRequest request) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		if(!StringUtils.hasText(request.getAssigneeId())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Assignee Required!!");
		}
		Optional<Task> task = taskRepository.findById(taskId);
		if(task.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Task not found!!");
		}
		if(!task.get().getAssignee().getId().equals(request.getAssigneeId())) {
			Optional<Employee> employee = employeeRepository.findById(request.getAssigneeId());
			if(employee.isEmpty()) {
				return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Assignee Not found!!");
			}
			task.get().getAssignee().setActiveTasks(task.get().getAssignee().getActiveTasks()-1);
			employeeRepository.save(task.get().getAssignee());
			Notifications notification = new Notifications();
			notification.setToId(task.get().getAssignee().getId());
			notification.setType(Constants.NOTI_TYPE_MIGRATED);
			notification.setTitle(Constants.TASK_MIGRATED);
			notification.setMessage("TaskId: " + task.get().getId() + " Migreated from you by Admin.");
			notification.setCreatedAt(new Date());
			notificationRepository.save(notification);
			employee.get().setActiveTasks(employee.get().getActiveTasks()+1);
			task.get().setAssignee(employee.get());
			employeeRepository.save(employee.get());
			Notifications notification2 = new Notifications();
			notification2.setToId(employee.get().getId());
			notification2.setType(Constants.NOTI_TYPE_ASSIGNED);
			notification2.setTitle(Constants.TASK_ASSIGN);
			notification2.setMessage("a new task " + "\"" + task.get().getTitle() + "\"" + " assigned to you.");
			notification2.setCreatedAt(new Date());
			notificationRepository.save(notification2);
		}else {
			Notifications notification = new Notifications();
			notification.setToId(task.get().getAssignee().getId());
			notification.setType(Constants.NOTI_TYPE_UPDATE);
			notification.setTitle(Constants.TASK_UPDATED);
			notification.setMessage("TaskId : " + task.get().getId() + " is updated by Admin " + " , Status : " + request.getStatus());
			notification.setCreatedAt(new Date());
			notificationRepository.save(notification);
		}
		Optional<Priority> priority = priorityRepository.findByCode(request.getPriority());
		task.get().setPriority(priority.get());
		task.get().setStatus(request.getStatus());
		if(request.getStatus().equals(Constants.COMPLETED)) {
			task.get().setActualCompletion(new Date());
		}
		task.get().setExpectedCompletionDate(request.getExpectedDate());
		task.get().setRemarks(request.getRemarks());
		task.get().setUpdatedBy(adminId);
		taskRepository.save(task.get());
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	public Response<String> deleteProject(String adminId, String projectId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<Project> project = projectRepository.findById(projectId);
		if(project.isEmpty()) {
		   return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Project Not Found");
		}
		if(project.get().getActiveTasks()>0) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Some active tasks in this project, delete tasks first!");
		}
		projectRepository.delete(project.get());
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	@Transactional
	public Response<String> deleteTaskByProjectId(String adminId, String projectId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<Project> project = projectRepository.findById(projectId);
		if(project.isEmpty()) {
		   return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Project Not Found");
		}
		List<Task> tasks = taskRepository.findTaskByProjectId(projectId);
		if(tasks.isEmpty()) {
			 return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No Active Tasks!!");
		}
		for(Task t:tasks) {
			Optional<Employee> emp = employeeRepository.findById(t.getAssignee().getId());
			emp.get().setActiveTasks(emp.get().getActiveTasks()-1);
			employeeRepository.save(emp.get());
		}
		project.get().setStatus(Constants.PENDING);
		project.get().setActiveTasks(0);
		projectRepository.save(project.get());
		taskRepository.deleteByProjectId(projectId);
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	@Transactional
	public Response<String> deleteTaskByEmployeeId(String adminId, String employeeId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<Employee> emp= employeeRepository.findById(employeeId);
		if(emp.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Employee Not Found");
		}
		List<Task> tasks = taskRepository.findTaskByEmployeeId(employeeId);
		if(tasks.isEmpty()) {
			 return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No Active Tasks!!");
		}
		for(Task t:tasks) {
			Optional<Project> project = projectRepository.findById(t.getProject().getId());
			project.get().setActiveTasks(project.get().getActiveTasks()-1);
		    projectRepository.save(project.get());
		}
		emp.get().setActiveTasks(0);
		employeeRepository.save(emp.get());
		taskRepository.deleteByEmployeeId(employeeId);
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	public Response<String> deleteTask(String adminId, String taskId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<Task> task = taskRepository.findById(taskId);
		if(task.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Task not found");
		}
		Optional<Employee> emp = employeeRepository.findById(task.get().getAssignee().getId());
		Optional<Project> project = projectRepository.findById(task.get().getProject().getId());
		emp.get().setActiveTasks(emp.get().getActiveTasks()-1);
		project.get().setActiveTasks(project.get().getActiveTasks()-1);
		employeeRepository.save(emp.get());
		projectRepository.save(project.get());
		Notifications noti = new Notifications();
		noti.setToId(task.get().getAssignee().getId());
		noti.setType(Constants.NOTI_TYPE_CLOSED);
		noti.setTitle(Constants.TASK_CLOSED);
		noti.setMessage("TaskId: " + task.get().getId() + " is Closed by Admin.");
		noti.setCreatedAt(new Date());
		notificationRepository.save(noti);
		taskRepository.delete(task.get());
		return new Response<>(HttpStatus.SC_OK,"Success");
	}

	@Override
	public Response<List<Employee>> getAllEmployees(String adminId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		List<Employee> emplist= employeeRepository.getAllEmployees();
		return new Response<>(HttpStatus.SC_OK,"Success",emplist);
	}

	@Override
	public Response<Employee> getEmployeeById(String adminId, String employeeId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if(employee.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Employee not found");
		}
		
		return new Response<>(HttpStatus.SC_OK,"Success",employee.get());
	}

	@Override
	public Response<List<Project>> getAllProject(String adminId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		List<Project> project = projectRepository.getAllProject();
		return new Response<>(HttpStatus.SC_OK,"Success",project);
	}

	@Override
	public Response<Project> getProjectById(String adminId, String ProjectId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<Project> project = projectRepository.findById(ProjectId);
		if(project.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Project not found");
		}
		return new Response<>(HttpStatus.SC_OK,"Success",project.get());
	}

	@Override
	public Response<List<Task>> getAllTask(String adminId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		List<Task> tasks = taskRepository.findAllTask();
		return new Response<>(HttpStatus.SC_OK,"Success",tasks);
	}

	@Override
	public Response<Task> getTaskById(String adminId, String taskId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<Task> task = taskRepository.findById(taskId);
		if(task.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Task not found");
		}
		return new Response<>(HttpStatus.SC_OK,"Success",task.get());
	}

	@Override
	public Response<List<Task>> getAllTasksByProjectId(String adminId, String projectId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		List<Task> taskList = taskRepository.findTaskByProjectId(projectId);
		if(taskList.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No task found");
		}
		return new Response<>(HttpStatus.SC_OK,"Success",taskList);
	}

	@Override
	public Response<List<Task>> getAllTasksByEmployeeId(String adminId, String employeeId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		List<Task> taskList = taskRepository.findTaskByEmployeeId(employeeId);
		if(taskList.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No task found");
		}
		return new Response<>(HttpStatus.SC_OK,"Success",taskList);
	}

	@Override
	public Response<Integer> countProjectByStatus(String adminId, String Status) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		int count = projectRepository.countProject(Status);
		Integer c = Integer.valueOf(count); 
		return new Response<>(HttpStatus.SC_OK,"Success",c);
	}

	@Override
	public Response<Integer> countTaskByStatus(String adminId, String Status) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		int count = taskRepository.countTaskByStatus(Status);
		Integer c = Integer.valueOf(count); 
		return new Response<>(HttpStatus.SC_OK,"Success",c);
	}

	@Override
	public Response<String> deleteEmployee(String adminId, String empId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<Employee> employee = employeeRepository.findById(empId);
		if(employee.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"No Employee not found");
		}
		if(employee.get().getActiveTasks()>0) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Some tasks assigned to this employee, migrate tasks first!");
		}
		employeeRepository.delete(employee.get());
		return new Response<>(HttpStatus.SC_OK,"Deleted Successfully!!");
	}

	@Override
	public Response<List<AdminNotifications>> getAllNotifications(String adminId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		List<AdminNotifications> notificationList = adminNotificationsRepository.adminNotification();
		notificationList.forEach(n->{
			 if(n.getType().equals(Constants.NOTI_TYPE_UPDATE)) {
				String emoji = new String(Character.toChars(0x1F4CC));
				n.setTitle(emoji + " "+ n.getTitle());
			}else{
				String emoji = new String(Character.toChars(0x2705));
				n.setTitle(emoji + " "+ n.getTitle());
			}
		});
		return new Response<>(HttpStatus.SC_OK,"Success",notificationList);
	}

	@Override
	public Response<String> deleteNotificationById(String adminId, String notificationId) {
		Optional<Admin> admin = adminRepository.findById(adminId);
		if(admin.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"Unauthorized");
		}
		Optional<AdminNotifications> notification = adminNotificationsRepository.findById(notificationId);
		if(notification.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"not found");
		}
		adminNotificationsRepository.delete(notification.get());
		return new Response<>(HttpStatus.SC_OK,"Success");
	}
	
}
