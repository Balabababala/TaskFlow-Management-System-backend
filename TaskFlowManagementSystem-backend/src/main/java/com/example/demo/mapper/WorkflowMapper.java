package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Workflow;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class WorkflowMapper {
	
	
	@Autowired
	private UserRepository userRepository;
	
	public WorkflowDto entityToDto(Workflow workflow){
		  WorkflowDto workflowDto = new WorkflowDto();
		  workflowDto.setId(workflow.getId());
		  workflowDto.setName(workflow.getName());
		  workflowDto.setVersion(workflow.getVersion());
		  workflowDto.setCreatedAt(workflow.getCreatedAt());
		  workflowDto.setCreatedBy(workflow.getCreatedBy().getId()); // 存 User id
		  return workflowDto;
	}
	
	public Workflow dtoToEntity(WorkflowDto workflowDto){
		Workflow workflow=new Workflow();
		workflow.setId(workflowDto.getId());
		workflow.setName(workflowDto.getName());
		workflow.setVersion(workflowDto.getVersion());
		workflow.setCreatedAt(workflowDto.getCreatedAt());
		User user = userRepository.findById(workflowDto.getCreatedBy()).orElseThrow(() -> new RuntimeException("User not found"));
		workflow.setCreatedBy(user);
		
		return workflow;
	}
}
