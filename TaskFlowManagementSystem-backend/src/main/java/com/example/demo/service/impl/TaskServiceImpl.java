package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.RoleNotMatchException;
import com.example.demo.exception.StatusNotFoundException;
import com.example.demo.exception.TaskAssigneeNotMatchException;
import com.example.demo.exception.TaskConflictException;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.model.dto.TaskDto;
import com.example.demo.model.entity.Status;
import com.example.demo.model.entity.Task;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.TaskAssigneeRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.TaskService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class TaskServiceImpl implements TaskService{

	
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private TaskAssigneeRepository taskAssigneeRepository;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private  ObjectMapper objectMapper;
	
	
	@Override
	public void createTask(CustomUserDetails customUserDetails, TaskDto taskDto) {
		//1.驗證傳入
		validate(taskDto);
		if (taskDto.getTitle() == null) throw new ValidationException("Title cannot be null");
        if (taskDto.getDescription() == null) throw new ValidationException("Description cannot be null");
        //2.驗證權限
        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"));
        if (!(isAdmin || isOwner )) {
            throw new RoleNotMatchException("admin or owner");
        }
        //3.執行
        Task currentTask=taskMapper.toEntity(taskDto);
        currentTask.setOwner(customUserDetails.getUser()); 
        currentTask.setStatus(statusRepository.findByWorkflowIdAndIsStartNodeTrue(taskDto.getWorkflowId()).orElseThrow(()->new StatusNotFoundException("find by workflowId:"+taskDto.getWorkflowId()+"can't find Status")));
        taskRepository.save(currentTask);
        
	}

	@Override
	public void transitionStatus(CustomUserDetails customUserDetails, TaskDto taskDto) {
		//1.驗證傳入
		validate(taskDto);
		//2.驗證權限
		Task existTask=taskRepository.findById(taskDto.getId()).orElseThrow(()-> new TaskNotFoundException(taskDto.getId()));
		Status currentStatus=existTask.getStatus();
		Long userId = customUserDetails.getUser().getId();
		boolean isAssigned = taskAssigneeRepository
			        .existsByTaskIdAndUserIdAndStatusId(existTask.getId(), userId, currentStatus.getId());
		if (!isAssigned) {
            throw new TaskAssigneeNotMatchException("TaskAssignee not match");
        }
		//3.該status 是否可合法轉換
		Set<Long> allNext = extractIdsFromJson(existTask.getStatus());
		if (!allNext.contains(taskDto.getStatusId())) {
			throw new ValidationException("不合法的狀態轉換。目標狀態 ID: " + taskDto.getStatusId());
		}
		//4.執行
		existTask.setStatus(statusRepository.findById(taskDto.getStatusId()).orElseThrow(()->new StatusNotFoundException(taskDto.getStatusId())));
		
		//要補 建 TaskHistory 方法
		taskMapper.updateEntity(taskDto,existTask);
		taskRepository.save(existTask);

	}


	
	@Override
	public void adminForceTransition(CustomUserDetails customUserDetails, TaskDto taskDto) {
		//1.驗證傳入
		validate(taskDto);
		//2.驗證權限
		boolean isAdmin = customUserDetails.getAuthorities().stream()
		                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		if (!isAdmin) {
		       throw new RoleNotMatchException("admin");
		}
		//3.執行
		Task existTask=taskRepository.findById(taskDto.getId()).orElseThrow(()-> new TaskNotFoundException(taskDto.getId()));
		existTask.setStatus(statusRepository.findById(taskDto.getStatusId()).orElseThrow(()->new StatusNotFoundException(taskDto.getStatusId())));
		
		//要補 建 TaskHistory 方法
		taskMapper.updateEntity(taskDto,existTask);
		taskRepository.save(existTask);
	
		
	}

	@Override
	public void deleteTask(CustomUserDetails customUserDetails, Long id) {
		//1.驗證傳入
		Task existTask = taskRepository.findById(id)
		        .orElseThrow(() -> new TaskNotFoundException(id));
		//2.驗證權限
		boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		boolean isOwner = customUserDetails.getUser().getId().equals(existTask.getOwner().getId());
		if (!(isAdmin || isOwner )) {
            throw new RoleNotMatchException("admin or owner");
        }
		//3.驗證狀態
		if(!existTask.getActive())throw new TaskConflictException("have removed");
		//4.執行
		existTask.setActive(false);
		taskRepository.save(existTask);
	}

	@Override
	public void restoreTask(CustomUserDetails customUserDetails, Long id) {
		//1.驗證傳入
		Task existTask = taskRepository.findById(id)
		        .orElseThrow(() -> new TaskNotFoundException(id));
		//2.驗證權限
		boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		boolean isOwner = customUserDetails.getUser().getId().equals(existTask.getOwner().getId());
		if (!(isAdmin || isOwner )) {
            throw new RoleNotMatchException("admin or owner");
        }
		//3.驗證狀態
		if(existTask.getActive())throw new TaskConflictException("have recovered");
		//4.執行
		existTask.setActive(true);
		taskRepository.save(existTask);
		
	}


	public TaskDto findTask(CustomUserDetails customUserDetails, Long id) {
	    // 1. 取得任務 (且必須是 Active)
	    Task task = taskRepository.findByIdAndActiveTrue(id)
	            .orElseThrow(() -> new TaskNotFoundException(id));
	    
	    Long currentUserId = customUserDetails.getUser().getId();

	    // 2. 權限檢查：必須是 Owner 或者是 Assignee 才有權限查看
	    boolean isOwner = task.getOwner().getId().equals(currentUserId);
	    boolean isAssignee = taskAssigneeRepository.existsByTaskIdAndUserId(id, currentUserId);

	    if (!isOwner && !isAssignee) {
	        throw new RoleNotMatchException("無權限查看此任務");
	    }

	    return taskMapper.toDto(task);
	}
	
	@Override
	public List<TaskDto> findAllTask(CustomUserDetails customUserDetails) {
		Long currentUserId = customUserDetails.getUser().getId();
		List<Task> tasks = taskRepository.findAllMyTasks(currentUserId);
		
		return tasks.stream()
				.map(taskMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public TaskDto findTaskAdminVer(CustomUserDetails customUserDetails, Long id) {
		
		 //1.驗證權限
        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
		       throw new RoleNotMatchException("admin");
		}
        //2.執行
		Task task = taskRepository.findById(id)
		        .orElseThrow(() -> new TaskNotFoundException(id));
	
		return taskMapper.toDto(task);
	}

	@Override
	public List<TaskDto> findAllTaskAdminVer(CustomUserDetails customUserDetails) {
		 //1.驗證權限
        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
		       throw new RoleNotMatchException("admin");
		}
		List<Task> tasks = taskRepository.findAll();

		return tasks.stream()
				.map(taskMapper::toDto)
				.collect(Collectors.toList());
	}

	
	private void validate(TaskDto taskDto) {
        if (taskDto.getWorkflowId() == null) throw new ValidationException("WorkflowId cannot be null");
        if (taskDto.getStatusId() == null) throw new ValidationException("StatusId cannot be null");
    }
	
	//解析json 用
	// 得到 status AllowedTransitions next 裡的所有
    private Set<Long> extractIdsFromJson(Status status) {
		Set<Long> allIds = new HashSet<>();
		 try {
	         if (status.getAllowedTransitions() != null && !status.getAllowedTransitions().isEmpty()) {
	             JsonNode root = objectMapper.readTree(status.getAllowedTransitions());
	             JsonNode nextNode = root.get("next");
	             
	             if (nextNode != null && nextNode.isArray()) {
	                 for (JsonNode idNode : nextNode) {
	                     allIds.add(idNode.asLong());                      
	                 }
	             }
	         }
	     } catch (Exception e) {
	         throw new ValidationException("JSON 格式錯誤: " + status.getAllowedTransitions());
	     }
		 return allIds;
    }
    
}
