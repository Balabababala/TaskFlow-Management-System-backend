package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.ApiResponse;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.WorkflowService;

@RestController
@RequestMapping("/api/admin/workflow")
public class AdminWorkflowController {

	@Autowired
	private WorkflowService workflowService; 


	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<WorkflowDto>> searchWorkflowAㄙㄩㄛ(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long id){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", workflowService.findWorkflowAdminVer(customUserDetails,id)));
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<WorkflowDto>>> searchAllWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", workflowService.findAllWorkflowAdminVer(customUserDetails)));
	}
	
}
