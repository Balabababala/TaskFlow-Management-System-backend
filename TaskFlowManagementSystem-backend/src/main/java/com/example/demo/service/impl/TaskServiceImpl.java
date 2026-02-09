package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.dto.TaskDto;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.TaskService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService{

	@Override
	public void createTask(CustomUserDetails customUserDetails, TaskDto taskDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTask(CustomUserDetails customUserDetails, TaskDto taskDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTask(CustomUserDetails customUserDetails, Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreTask(CustomUserDetails customUserDetails, Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TaskDto findTask(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskDto> findAllWorkflow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskDto findTaskAdminVer(CustomUserDetails customUserDetails, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskDto> findAllWorkflowAdminVer(CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private void validate(TaskDto taskDto) {
        if (taskDto.getWorkflowId() == null) throw new ValidationException("WorkflowId cannot be null");
        if (taskDto.getStatusId() == null) throw new ValidationException("StatusId cannot be null");
        if (taskDto.getTitle() == null) throw new ValidationException("Title cannot be null");
        if (taskDto.getDescription() == null) throw new ValidationException("Description cannot be null");
    }
}
