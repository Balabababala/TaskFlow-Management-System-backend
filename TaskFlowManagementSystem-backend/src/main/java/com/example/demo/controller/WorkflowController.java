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
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.WorkflowService;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowController {

	@Autowired
	private WorkflowService workflowService; 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WorkflowMapper workflowMapper;
	///
	///目前 測試 http://localhost:8080/api/workflow/create
	///Json
	///{
	///	    "id": null,
	///	    "name": "lose",
	///	    "version": 1,
	///	    "createdAt": null,
	///	    "createdBy": 1          
	///	}
	///

	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Void>> createWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails , @RequestBody WorkflowDto workflowDto){
	    
	    workflowService.createWorkflow(customUserDetails,workflowDto);
	    return ResponseEntity.ok(ApiResponse.success("創建成功", null));

	}
	
	
	
	@PostMapping("/update")
	public ResponseEntity<ApiResponse<Void>> updateWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails ,@RequestBody WorkflowDto workflowDto){

	    
	    workflowService.updateWorkflow(customUserDetails,workflowDto);
	    return ResponseEntity.ok(ApiResponse.success("更改成功", null));
	}
	

	@DeleteMapping("/delete/{id}")
	
	public ResponseEntity<ApiResponse<Void>> deleteWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id){
	    
	    workflowService.deleteWorkflow(customUserDetails ,id);
	    return ResponseEntity.ok(ApiResponse.success("刪除成功", null));
	}
	///
		///目前 測試 http://localhost:8080/api/workflow/restore
		///Json
		///{
		///	    "id": 1,
		///	    "name": null,
		///	    "version": null,
		///	    "createdAt": null,
		///	    "createdBy": 1          
		///	}
		///配合創建測 id 為刪除的目標
		///
	@PostMapping("/restore/{id}")
	public ResponseEntity<ApiResponse<Void>> restoreWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long id){
	    
	    workflowService.restoreWorkflow(customUserDetails ,id);
	    return ResponseEntity.ok(ApiResponse.success("回復成功", null));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<WorkflowDto>> searchWorkflow(@PathVariable Long id){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", workflowService.findWorkflow(id)));
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<WorkflowDto>>> searchAllWorkflow(){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", workflowService.findAllWorkflow()));
	}
	
	

}
