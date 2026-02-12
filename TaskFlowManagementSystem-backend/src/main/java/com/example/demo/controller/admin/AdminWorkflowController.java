package com.example.demo.controller.admin;

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

import com.example.demo.model.dto.ApiResponse;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.dto.WorkflowRequest;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.WorkflowService;

@RestController
@RequestMapping("/api/admin/workflow")
public class AdminWorkflowController {

	@Autowired
	private WorkflowService workflowService; 


	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Void>> createWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails , @RequestBody WorkflowRequest workflowRequest){
	    
	    workflowService.createWorkflow(customUserDetails,workflowRequest);
	    return ResponseEntity.ok(ApiResponse.success("創建成功", null));

	}	
	
	@PostMapping("/update")
	public ResponseEntity<ApiResponse<Void>> updateWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails ,@RequestBody WorkflowRequest workflowRequest){

	    
	    workflowService.updateWorkflow(customUserDetails,workflowRequest);
	    return ResponseEntity.ok(ApiResponse.success("更改成功", null));
	}
	
	@DeleteMapping("/delete/{id}")
	
	public ResponseEntity<ApiResponse<Void>> deleteWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id){
	    
	    workflowService.deleteWorkflow(customUserDetails ,id);
	    return ResponseEntity.ok(ApiResponse.success("刪除成功", null));
	}

	@PostMapping("/restore/{id}")
	public ResponseEntity<ApiResponse<Void>> restoreWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long id){
	    
	    workflowService.restoreWorkflow(customUserDetails ,id);
	    return ResponseEntity.ok(ApiResponse.success("回復成功", null));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<WorkflowDto>> searchWorkflowAㄙㄩㄛ(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long id){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", workflowService.findWorkflowAdminVer(customUserDetails,id)));
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<WorkflowDto>>> searchAllWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", workflowService.findAllWorkflowAdminVer(customUserDetails)));
	}
	
}
