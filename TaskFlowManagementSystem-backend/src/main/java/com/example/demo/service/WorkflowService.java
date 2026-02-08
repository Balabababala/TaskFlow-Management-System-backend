package com.example.demo.service;

import java.util.List;



import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.dto.WorkflowRequest;
import com.example.demo.secure.CustomUserDetails;

	public interface WorkflowService {
		void createWorkflow(CustomUserDetails customUserDetails,WorkflowRequest workflowRequest);
		
		void updateWorkflow(CustomUserDetails customUserDetails,WorkflowRequest workflowRequest);
		
		void deleteWorkflow(CustomUserDetails customUserDetails ,Long id);
		
		void restoreWorkflow(CustomUserDetails customUserDetails,Long id);
		
		
		WorkflowDto findWorkflow(Long id);
		List<WorkflowDto> findAllWorkflow();

		WorkflowDto findWorkflowAdminVer(CustomUserDetails customUserDetails,Long id);
		List<WorkflowDto> findAllWorkflowAdminVer(CustomUserDetails customUserDetails);
}
