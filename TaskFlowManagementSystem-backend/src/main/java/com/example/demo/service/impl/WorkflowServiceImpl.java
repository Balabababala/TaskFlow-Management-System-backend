package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.WorkflowMapper;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.Workflow;
import com.example.demo.repository.WorkflowRepository;
import com.example.demo.service.WorkflowService;

@Service
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
		Workflow workflow = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("workflow not found"));
		
		if (!workflow.getActive()) {
		    throw new IllegalStateException("workflow already deleted");
		}
		
		workflow.setActive(false);
		workflowRepository.save(workflow);
	};
	
	public WorkflowDto findWorkflow(Long id) {
		Workflow workflow = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		return workflowMapper.entityToDto(workflow);
		
	};
	
	public List<WorkflowDto> findAllWorkflow(){
		return workflowRepository.findAll().stream()
		.map(workflowMapper::entityToDto)
		.collect(Collectors.toList());
	};
}
