package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.repository.RoleRepository;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
	
	@Autowired 
	private RoleRepository roleRepository;
	
	
	 @Mapping(source = "role.id", target = "roleId")
	 abstract UserDto toDto(User user);
	
	 
	 // --- DTO 轉 Entity (新增時使用) ---
	 @Mapping(target = "id", ignore = true)        // 新增時 ID 由資料庫自動生成
	 public abstract  User toEntity(UserDto userDto);

	    
	 // --- DTO 更新至現有的 Entity (更新時使用) ---
	 @Mapping(target = "id", ignore = true)        // 防止 ID 被修改
	 @Mapping(source = "roleId", target = "role") 
	 public abstract void updateEntity(UserDto userDto, @MappingTarget User user);
	 
	// MapStruct 看到需要把 Integer 轉 Role 時，會自動呼叫此方法
	protected Role mapIdToRole(Integer roleId) {
	    if (roleId == null) return null;
	    return roleRepository.findById(roleId).orElse(null);
	}
}
