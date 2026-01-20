package com.example.demo.service;

import java.util.List;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.secure.CustomUserDetails;

	public interface WorkflowService {
		void createWorkflow(CustomUserDetails customUserDetails,WorkflowDto workflowDto);
		
		void updateWorkflow(WorkflowDto workflowDto);
		
		void deleteWorkflow(Long id);
		
		void restoreWorkflow(Long id);
		
		WorkflowDto findWorkflow(Long id);
		List<WorkflowDto> findAllWorkflow();

}
