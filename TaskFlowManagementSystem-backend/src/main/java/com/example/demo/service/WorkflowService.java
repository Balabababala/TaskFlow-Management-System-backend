package com.example.demo.service;

import java.util.List;
import com.example.demo.model.dto.WorkflowDto;

	public interface WorkflowService {
		void createWorkflow(WorkflowDto workflowDto);
		
		void updateWorkflow(WorkflowDto workflowDto);
		
		void deleteWorkflow(Long id);
		
		WorkflowDto findWorkflow(Long id);
		List<WorkflowDto> findAllWorkflow();

}
