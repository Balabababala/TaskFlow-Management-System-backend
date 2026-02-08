package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.exception.RoleNotMatchException;
import com.example.demo.exception.StatusMasterNotFoundException;
import com.example.demo.exception.UserNotMatchException;
import com.example.demo.mapper.WorkflowMapper;
import com.example.demo.model.dto.StatusDto;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.dto.WorkflowRequest;
import com.example.demo.model.entity.Status;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Workflow;
import com.example.demo.repository.StatusMasterRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkflowRepository;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.WorkflowService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class WorkflowServiceImpl implements WorkflowService{
	
	@Autowired 
	private WorkflowRepository workflowRepository;
	@Autowired
	private WorkflowMapper workflowMapper;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private StatusMasterRepository statusMasterRepository;
	@Autowired
	private  ObjectMapper objectMapper;
	
	
	
	public void createWorkflow(CustomUserDetails customUserDetails,WorkflowRequest workflowRequest){
        // 1.取資料+基礎校驗
		WorkflowDto workflowDto=workflowRequest.getWorkflow();
		List<StatusDto> statusDtos= workflowRequest.getStatusDtos();
		validateWorkflowDto(workflowDto);
        validatestatusDtos(statusDtos);

        // 2.檢查權限 可以去掉 filter 會擋
        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new RoleNotMatchException("admin");
        }
        
        // 3. 檢查重複
        if (workflowRepository.existsByName(workflowDto.getName())) {
            throw new IllegalArgumentException("Workflow name already exists");
        }
        // 4. 檢查statusDtos 的 next 的是否存在
        Set<Long> checkNext = extractAllIdsFromJson(statusDtos);
        for (Long id : checkNext) {
            if (!statusMasterRepository.existsById(id)) {
                // 這樣你可以精確指出是哪個 ID 有問題
                throw new StatusMasterNotFoundException("找不到 ID 為 " + id + " 的狀態主資料");
            }
        }
        
        
        //5.存入
        workflowDto.setVersion(1);
        create(workflowDto,statusDtos,customUserDetails.getUser());
        
    }
	
	 public void updateWorkflow(CustomUserDetails customUserDetails,WorkflowRequest workflowRequest) {
		 
	 	// 1.取資料+ 基礎校驗 
		WorkflowDto workflowDto=workflowRequest.getWorkflow();
		List<StatusDto> statusDtos= workflowRequest.getStatusDtos();
		
        validateWorkflowDto(workflowDto);       
        if (workflowDto.getId() == null) {
            throw new IllegalArgumentException("WorkflowDto id cannot be null");
        }
        validatestatusDtos(statusDtos);
        
        // 2.檢查權限(身分)
        Workflow existingWorkflow = workflowRepository.findLatestWorkflowsById(workflowDto.getId())
                .orElseThrow(() -> new RuntimeException("workflow not found"));
        Long yourId = customUserDetails.getUser().getId();
        Long createByid =existingWorkflow.getCreatedBy().getId();
        boolean isYours= (yourId ==createByid);
        if (!isYours) {
            throw new UserNotMatchException(yourId,createByid);
        }
        // 3. 檢查statusDtos 的 next 的是否存在
        Set<Long> checkNext = extractAllIdsFromJson(statusDtos);
        for (Long id : checkNext) {
            if (!statusMasterRepository.existsById(id)) {
                // 這樣你可以精確指出是哪個 ID 有問題
                throw new StatusMasterNotFoundException("找不到 ID 為 " + id + " 的狀態主資料");
            }
        }
      //4.存入
        workflowDto.setVersion(existingWorkflow.getVersion()+1);     
        create(workflowDto,statusDtos,customUserDetails.getUser());
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
		return workflowRepository.findLatestWorkflows().stream()
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
    private void validateWorkflowDto(WorkflowDto workflowtoDto) {
        if (workflowtoDto.getName() == null) throw new IllegalArgumentException("Name cannot be null");

    }
    private void validatestatusDtos(List<StatusDto> statusDtos) {
    	for(StatusDto statusDto:statusDtos) {
    		if (statusDto.getStatusMasterId() == null) throw new IllegalArgumentException("StatusMasterId cannot be null");
    		if (statusDto.getAllowedTransitions() == null) throw new IllegalArgumentException("AllowedTransitions cannot be null");
    	}
    	
    }
    
    private void create(WorkflowDto workflowDto,List<StatusDto> statusDtos,User user) {

    	
    	
        Workflow workflow = workflowMapper.toEntity(workflowDto);
        workflow.setCreatedBy(user);
    	workflow =workflowRepository.save(workflow);
        
        Workflow finalWorkflow = workflow; // Lambda 內部使用需要有效最終變數
        List<Status> statusList = statusDtos.stream()
                .map(statusDto -> {
                    Status status = new Status();
                    status.setWorkflow(finalWorkflow);
                    status.setAllowedTransitions(statusDto.getAllowedTransitions());
                    
                    // 補上對 StatusMaster 的關聯 (使用 getReferenceById 效能最好)
                    status.setStatusMaster(statusMasterRepository.getReferenceById(statusDto.getStatusMasterId()));
                    
                    return status;
                })
                .collect(Collectors.toList());

        statusRepository.saveAll(statusList);   
    }
    
    //解析json 用
    
    private Set<Long> extractAllIdsFromJson(List<StatusDto> statusDtos) {
        Set<Long> allIds = new HashSet<>();
        
        for (StatusDto dto : statusDtos) {
            // 1. 先加入節點本身的 ID
            allIds.add(dto.getStatusMasterId());
            
            // 2. 解析 JSON 字串
            try {
                if (dto.getAllowedTransitions() != null && !dto.getAllowedTransitions().isEmpty()) {
                    JsonNode root = objectMapper.readTree(dto.getAllowedTransitions());
                    JsonNode nextNode = root.get("next");
                    
                    if (nextNode != null && nextNode.isArray()) {
                        for (JsonNode idNode : nextNode) {
                            allIds.add(idNode.asLong());
                        }
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("JSON 格式錯誤: " + dto.getAllowedTransitions());
            }
        }
        return allIds;
    }
}


//exception 處裡記得改



















