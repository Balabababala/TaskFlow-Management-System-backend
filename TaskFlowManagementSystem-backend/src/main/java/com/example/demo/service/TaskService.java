package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.TaskDto;
import com.example.demo.secure.CustomUserDetails;

public interface TaskService {
		void createTask(CustomUserDetails customUserDetails,TaskDto taskDto);
		
		void transitionStatus(CustomUserDetails customUserDetails,TaskDto taskDto);
		
		void adminForceTransition(CustomUserDetails customUserDetails,TaskDto taskDto);
		
		void deleteTask(CustomUserDetails customUserDetails ,Long id);
		
		void restoreTask(CustomUserDetails customUserDetails,Long id);
		
		
		TaskDto findTask(CustomUserDetails customUserDetails,Long id);
		List<TaskDto> findAllTask(CustomUserDetails customUserDetails);

		TaskDto findTaskAdminVer(CustomUserDetails customUserDetails,Long id);
		List<TaskDto> findAllTaskAdminVer(CustomUserDetails customUserDetails);
}
