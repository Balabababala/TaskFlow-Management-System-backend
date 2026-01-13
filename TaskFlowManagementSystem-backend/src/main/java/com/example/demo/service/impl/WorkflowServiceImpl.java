package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.mapper.WorkflowMapper;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.Workflow;
import com.example.demo.repository.WorkflowRepository;
import com.example.demo.service.WorkflowService;

public class WorkflowServiceImpl implements WorkflowService{
	
	@Autowired 
	private WorkflowRepository workflowRepository;
	@Autowired
	private WorkflowMapper workflowMapper;
	
	public void createWorkflow(WorkflowDto workflowDto){
		Workflow workflow = workflowMapper.dtoToEntity(workflowDto);
		workflowRepository.save(workflow);
	};
	
	public void updateWorkflow(Long id,WorkflowDto workflowDto) {		
		Workflow workflow = workflowMapper.dtoToEntity(workflowDto);
		workflow.setId(id);
		workflowRepository.save(workflow);
	};
	
	public void deleteWorkflow(Long id) {
		
	};
	
	public WorkflowDto findWorkflow(Long id) {
		
	};
	public List<WorkflowDto> findAllWorkflow(){
		
	};
}
