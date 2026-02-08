package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.Workflow;


@Mapper(componentModel = "spring")
public interface WorkflowMapper {



	/*
	public WorkflowDto entityToDto(Workflow workflow){
		  WorkflowDto workflowDto = new WorkflowDto();
		  workflowDto.setId(workflow.getId());
		  workflowDto.setName(workflow.getName());
		  workflowDto.setVersion(workflow.getVersion());
		  workflowDto.setCreatedAt(workflow.getCreatedAt());
		  workflowDto.setUpdatedAt(workflow.getUpdatedAt());
		  workflowDto.setCreatedBy(workflow.getCreatedBy().getId()); // 存 User id
		  return workflowDto;
	}
	*/
	

	    WorkflowDto toDto(Workflow workflow);
	
	
	/*
	public Workflow dtoToEntity(WorkflowDto workflowDto){
		Workflow workflow=new Workflow();
		workflow.setId(workflowDto.getId());
		workflow.setName(workflowDto.getName());
		workflow.setVersion(workflowDto.getVersion());
		User user = userRepository.findById(workflowDto.getCreatedBy()).orElseThrow(() -> new RuntimeException("User not found"));
		workflow.setCreatedBy(user);
		
		return workflow;
	}
	*/	
	    // --- DTO 轉 Entity (新增時使用) ---
	    @Mapping(target = "id", ignore = true)        // 新增時 ID 由資料庫自動生成
	    @Mapping(target = "createdAt", ignore = true) // 依賴 DB DEFAULT 或 JPA Auditing
	    @Mapping(target = "updatedAt", ignore = true) // 依賴 DB DEFAULT 或 JPA Auditing
	    @Mapping(target = "createdBy", ignore = true) // 在 Service 層手動關聯 User
	    Workflow toEntity(WorkflowDto workflowDto);

	    // --- DTO 更新至現有的 Entity (更新時使用) --- 用不到了 更新是 version +1 insert 新的
	    @Mapping(target = "id", ignore = true)        // 防止 ID 被修改
	    @Mapping(target = "createdAt", ignore = true) // 禁止修改建立時間
	    @Mapping(target = "updatedAt", ignore = true) // 更新時間交給 Auditing 或 Service
	    @Mapping(target = "createdBy", ignore = true) // 建立者通常不允許修改
	    void updateEntity(WorkflowDto workflowDto, @MappingTarget Workflow workflow);
	    
}
