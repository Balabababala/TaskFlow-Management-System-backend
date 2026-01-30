package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.model.dto.StatusMasterDto;
import com.example.demo.model.entity.StatusMaster;


@Mapper(componentModel = "spring")
public interface StatusMasterMapper {

	    StatusMasterDto toDto(StatusMaster statusMaster);
	
	    // --- DTO 轉 Entity (新增時使用) ---
	    @Mapping(target = "id", ignore = true)        // 新增時 ID 由資料庫自動生成
	    @Mapping(target = "createdAt", ignore = true) // 依賴 DB DEFAULT 或 JPA Auditing
	    @Mapping(target = "updatedAt", ignore = true) // 依賴 DB DEFAULT 或 JPA Auditing
	    @Mapping(source = "createdBy", target = "createdBy")
	    @Mapping(source = "updatedBy", target = "updatedBy")
	    StatusMaster toEntity(StatusMasterDto statusMaster);

	    // --- DTO 更新至現有的 Entity (更新時使用) ---
	    @Mapping(target = "id", ignore = true)        // 防止 ID 被修改
	    @Mapping(target = "createdAt", ignore = true) // 禁止修改建立時間
	    @Mapping(target = "updatedAt", ignore = true) // 更新時間交給 Auditing 或 Service
	    @Mapping(target = "code", ignore = true)
	    @Mapping(target = "active", ignore = true)
	    @Mapping(source = "createdBy", target = "createdBy")
	    @Mapping(source = "updatedBy", target = "updatedBy")
	    void updateEntity(StatusMasterDto statusMasterDto, @MappingTarget StatusMaster StatusMaster);
	    
}
