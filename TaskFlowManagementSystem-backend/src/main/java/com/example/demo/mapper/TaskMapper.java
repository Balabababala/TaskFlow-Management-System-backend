package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.model.dto.TaskDto;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.Task;
import com.example.demo.model.entity.Workflow;


@Mapper(componentModel = "spring")
public interface TaskMapper {


	TaskDto toDto(Task task);	

	// --- DTO 轉 Entity (新增時使用) ---
    @Mapping(target = "id", ignore = true)        // 防止 ID 被修改
    @Mapping(target = "createdAt", ignore = true) // 禁止修改建立時間
    @Mapping(target = "updatedAt", ignore = true) // 更新時間交給 Auditing 或 Service
	Task toEntity(TaskDto taskDto);

		
	@Mapping(target = "id", ignore = true)        // 防止 ID 被修改
	@Mapping(target = "createdAt", ignore = true) // 禁止修改建立時間
	@Mapping(target = "updatedAt", ignore = true) // 更新時間交給 Auditing 或 Service
	void updateEntity(TaskDto taskDto, @MappingTarget Task task);
	    
}
