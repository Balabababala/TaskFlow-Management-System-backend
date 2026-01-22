package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.exception.RoleNotMatchException;
import com.example.demo.exception.UserNotMatchException;
import com.example.demo.mapper.WorkflowMapper;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.Workflow;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkflowRepository;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.WorkflowService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class WorkflowServiceImpl implements WorkflowService{
	
	@Autowired 
	private WorkflowRepository workflowRepository;
	@Autowired
	private WorkflowMapper workflowMapper;

	
	
	public void createWorkflow(CustomUserDetails customUserDetails,WorkflowDto workflowDto) {
        // 1. 基礎校驗
        validateDto(workflowDto);

        // 2.檢查權限
        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new RoleNotMatchException("admin");
        }
        
        // 3. 檢查重複
        if (workflowRepository.existsByNameAndVersion(workflowDto.getName(), workflowDto.getVersion())) {
            throw new IllegalArgumentException("Workflow name + version already exists");
        }
        
        // 4. 轉換與儲存
        Workflow workflow = workflowMapper.toEntity(workflowDto);

        workflow.setCreatedBy(customUserDetails.getUser());
        workflowRepository.save(workflow);
    }
	
	 public void updateWorkflow(CustomUserDetails customUserDetails,WorkflowDto workflowDto) {
		 
	 	// 1. 基礎校驗 
        validateDto(workflowDto);
        if (workflowDto.getId() == null) {
            throw new IllegalArgumentException("WorkflowDto id cannot be null");
        }
        
        // 2.檢查權限(身分)
        Workflow existingWorkflow = workflowRepository.findById(workflowDto.getId())
                .orElseThrow(() -> new RuntimeException("workflow not found"));
        Long yourId = customUserDetails.getUser().getId();
        Long createByid =existingWorkflow.getCreatedBy().getId();
        boolean isYours= (yourId ==createByid);
        if (!isYours) {
            throw new UserNotMatchException(yourId,createByid);
        }
        
        // 3. 檢查重複（排除自己：如果名稱或版本變了，才需要查有沒有跟別人撞名） SQL 會擋
       

        // 4. 使用 MapStruct 自動更新欄位
        // 只要你在 Mapper 有寫 ignore = true，這裡就不會蓋掉 createdAt / createdBy
        workflowMapper.updateEntity(workflowDto, existingWorkflow);

        // 5. 儲存
        workflowRepository.save(existingWorkflow);
    }
	
	public void deleteWorkflow(@AuthenticationPrincipal CustomUserDetails customUserDetails ,Long id) {
		//1. 基礎校驗
		Workflow workflow = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("workflow not found"));
		
		if (!workflow.getActive()) {
		    throw new IllegalStateException("workflow already deleted");
		}
		//2. 檢查權限(身分)

		Long yourId = customUserDetails.getUser().getId();
        Long createByid =workflow.getCreatedBy().getId();
        boolean isYours= (yourId ==createByid);
        if (!isYours) {
            throw new UserNotMatchException(yourId,createByid);
        }
		
		workflow.setActive(false);
		workflowRepository.save(workflow);
	};
	
	public void restoreWorkflow(CustomUserDetails customUserDetails,Long id) {
		//1. 基礎校驗
		Workflow workflow = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("workflow not found"));
		
		if (workflow.getActive()) {
		    throw new IllegalStateException("workflow already active");
		}
		//2. 檢查權限(身分)
		
		Long yourId = customUserDetails.getUser().getId();
        Long createByid =workflow.getCreatedBy().getId();
        boolean isYours= (yourId ==createByid);
        if (!isYours) {
            throw new UserNotMatchException(yourId,createByid);
        }
		workflow.setActive(true);
		workflowRepository.save(workflow);
	};
	
	public WorkflowDto findWorkflow(Long id) {
		Workflow workflow = workflowRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new RuntimeException("workflow not found"));
		return workflowMapper.toDto(workflow);
		
	};
	
	
	
	public List<WorkflowDto> findAllWorkflow(){
		return workflowRepository.findByActiveTrue().stream()
		.map(workflowMapper::toDto)
		.collect(Collectors.toList());
	};
	
	public WorkflowDto findWorkflowAdminVer(CustomUserDetails customUserDetails,Long id) {
		 // 1.檢查權限
        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new RoleNotMatchException("admin");
        }
		
		
		Workflow workflow = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("workflow not found"));
		return workflowMapper.toDto(workflow);
		
	};
	
	
	
	public List<WorkflowDto> findAllWorkflowAdminVer(CustomUserDetails customUserDetails){
		 // 1.檢查權限
        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new RoleNotMatchException("admin");
        }
		
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



















