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
		boolean exists = workflowRepository.existsByNameAndVersion(workflowDto.getName(), workflowDto.getVersion());
		if (exists) {
		    throw new IllegalArgumentException("Workflow name + version already exists");
		}
		
	    if (workflowDto.getName() == null) {
	        throw new IllegalArgumentException("WorkflowDto name cannot be null");
	    }
	    if (workflowDto.getVersion() == null) {
	        throw new IllegalArgumentException("WorkflowDto version cannot be null");
	    }
	    
		Workflow workflow = workflowMapper.dtoToEntity(workflowDto);
		// 等SpringSecure 補好 要加createBy 
		workflowRepository.save(workflow);
	};
	
	public void updateWorkflow(WorkflowDto workflowDto) {		

		
	    if (workflowDto.getId() == null) {
	        throw new IllegalArgumentException("WorkflowDto id cannot be null");
	    }
	    if (workflowDto.getName() == null) {
	        throw new IllegalArgumentException("WorkflowDto name cannot be null");
	    }
	    if (workflowDto.getVersion() == null) {
	        throw new IllegalArgumentException("WorkflowDto Version cannot be null");
	    }
	    
	    // 1️⃣ 先從 DB 拿出 workflow
	    Workflow workflow = workflowRepository.findById(workflowDto.getId())
	            .orElseThrow(() -> new RuntimeException("workflow not found"));

	    // 2️⃣ 檢查 name + version 是否重複（排除自己）
	    boolean exists = workflowRepository.existsByNameAndVersion(workflowDto.getName(), workflowDto.getVersion());
	    if (exists && (!workflow.getName().equals(workflowDto.getName()) || !workflow.getVersion().equals(workflowDto.getVersion()))) {
	        throw new IllegalArgumentException("Workflow name + version already exists");
	    }

	    // 3️⃣ 更新欄位
	    workflow.setName(workflowDto.getName());
	    workflow.setVersion(workflowDto.getVersion());
	    // 其他欄位例如 createdBy、createdAt 不動
	    // updatedAt Hibernate 自動更新

	    // 4️⃣ 儲存
	    workflowRepository.save(workflow);
	    // 等SpringSecure 補好 要加createBy 
	    
	};
	
	public void deleteWorkflow(Long id) {
		Workflow workflow = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("workflow not found"));
		
		if (!workflow.getActive()) {
		    throw new IllegalStateException("workflow already deleted");
		}
		// 等SpringSecure 補好 要加身分檢查
		workflow.setActive(false);
		workflowRepository.save(workflow);
	};
	
	public void restoreWorkflow(Long id) {
		Workflow workflow = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("workflow not found"));
		
		if (workflow.getActive()) {
		    throw new IllegalStateException("workflow already active");
		}
		// 等SpringSecure 補好 要加身分檢查
		workflow.setActive(true);
		workflowRepository.save(workflow);
	};
	
	public WorkflowDto findWorkflow(Long id) {
		Workflow workflow = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("workflow not found"));
		return workflowMapper.entityToDto(workflow);
		
	};
	
	
	
	public List<WorkflowDto> findAllWorkflow(){
		return workflowRepository.findAll().stream()
		.map(workflowMapper::entityToDto)
		.collect(Collectors.toList());
	};
}
