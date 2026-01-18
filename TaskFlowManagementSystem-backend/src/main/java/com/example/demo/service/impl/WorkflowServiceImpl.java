package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.WorkflowMapper;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.Workflow;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkflowRepository;
import com.example.demo.service.WorkflowService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class WorkflowServiceImpl implements WorkflowService{
	
	@Autowired 
	private WorkflowRepository workflowRepository;
	@Autowired
	private WorkflowMapper workflowMapper;
	@Autowired
	private UserRepository userRepository;
	
	public void createWorkflow(WorkflowDto workflowDto) {
	        // 1. 基礎校驗
	        validateDto(workflowDto);

	        // 2. 檢查重複
	        if (workflowRepository.existsByNameAndVersion(workflowDto.getName(), workflowDto.getVersion())) {
	            throw new IllegalArgumentException("Workflow name + version already exists");
	        }
	        
	        // 3. 轉換與儲存
	        Workflow workflow = workflowMapper.toEntity(workflowDto);

	        workflow.setCreatedBy(userRepository.findById(1L).orElseThrow());//記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改記的改
	        workflowRepository.save(workflow);
	    }
	
	 public void updateWorkflow(WorkflowDto workflowDto) {
	        if (workflowDto.getId() == null) {
	            throw new IllegalArgumentException("WorkflowDto id cannot be null");
	        }
	        validateDto(workflowDto);

	        // 1️⃣ 先從 DB 拿出現有的實體 (真身)
	        Workflow existingWorkflow = workflowRepository.findById(workflowDto.getId())
	                .orElseThrow(() -> new RuntimeException("workflow not found"));

	        // 2️⃣ 檢查重複（排除自己：如果名稱或版本變了，才需要查有沒有跟別人撞名）
	        boolean nameChanged = !existingWorkflow.getName().equals(workflowDto.getName());
	        boolean versionChanged = !existingWorkflow.getVersion().equals(workflowDto.getVersion());
	        
	        if (nameChanged || versionChanged) {
	            if (workflowRepository.existsByNameAndVersion(workflowDto.getName(), workflowDto.getVersion())) {
	                throw new IllegalArgumentException("Workflow name + version already exists");
	            }
	        }

	        // 3️⃣ 使用 MapStruct 自動更新欄位
	        // 只要你在 Mapper 有寫 ignore = true，這裡就不會蓋掉 createdAt / createdBy
	        workflowMapper.updateEntity(workflowDto, existingWorkflow);

	        // 4️⃣ 儲存
	        workflowRepository.save(existingWorkflow);
	    }
	
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
		return workflowMapper.toDto(workflow);
		
	};
	
	
	
	public List<WorkflowDto> findAllWorkflow(){
		return workflowRepository.findAll().stream()
		.map(workflowMapper::toDto)
		.collect(Collectors.toList());
	};
	
	// 將重複的校驗邏輯抽出成私有方法
    private void validateDto(WorkflowDto workflowtoDto) {
        if (workflowtoDto.getName() == null) throw new IllegalArgumentException("Name cannot be null");
        if (workflowtoDto.getVersion() == null) throw new IllegalArgumentException("Version cannot be null");
    }
}





//SpringSecure 驗證相關記得改
//exception 處裡記得改



















