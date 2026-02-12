package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.RoleNotMatchException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.WorkflowMapper;
import com.example.demo.model.dto.ApiResponse;
import com.example.demo.model.dto.TaskDto;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.dto.WorkflowRequest;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.TaskService;
import com.example.demo.service.WorkflowService;

@RestController
@RequestMapping("/api/workflow")
public class TaskController {

	@Autowired
	private TaskService taskService; 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WorkflowMapper workflowMapper;
	

	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Void>> createWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails , @RequestBody TaskDto taskDto){
		taskService.createTask(customUserDetails,taskDto);
	    return ResponseEntity.ok(ApiResponse.success("創建成功", null));
	}
	
	
	@PostMapping("/transition")
	public ResponseEntity<ApiResponse<Void>> updateWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails ,@RequestBody TaskDto taskDto){

	    
		taskService.transitionStatus(customUserDetails,taskDto);
	    return ResponseEntity.ok(ApiResponse.success("更改成功", null));
	}
	

	@DeleteMapping("/delete/{id}")
	
	public ResponseEntity<ApiResponse<Void>> deleteWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id){
	    
		taskService.deleteTask(customUserDetails ,id);
	    return ResponseEntity.ok(ApiResponse.success("刪除成功", null));
	}

	@PostMapping("/restore/{id}")
	public ResponseEntity<ApiResponse<Void>> restoreWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long id){
	    
		taskService.restoreTask(customUserDetails ,id);
	    return ResponseEntity.ok(ApiResponse.success("回復成功", null));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<TaskDto>> searchWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long id){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", taskService.findTask(customUserDetails,id)));
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<TaskDto>>> searchAllWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", taskService.findAllTask(customUserDetails)));
	}
	
	

}
