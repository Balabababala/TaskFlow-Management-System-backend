package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.RoleDto;

public interface RoleService {

	 RoleDto findRole(Integer id);
	 
	 List<RoleDto> findAllRole();
}
