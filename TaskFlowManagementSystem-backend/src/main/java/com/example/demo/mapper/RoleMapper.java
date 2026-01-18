package com.example.demo.mapper;

import org.mapstruct.Mapper;


import com.example.demo.model.dto.RoleDto;
import com.example.demo.model.entity.Role;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {
	
	public abstract RoleDto toDto(Role role);
	 
}
