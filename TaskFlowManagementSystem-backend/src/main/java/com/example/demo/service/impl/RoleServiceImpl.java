package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.RoleNotFoundException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.model.dto.RoleDto;
import com.example.demo.model.entity.Role;

import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public RoleDto findRole(Integer id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));	
		return roleMapper.toDto(role);
	}

	@Override
	public List<RoleDto> findAllRole() {
		return roleRepository.findAll().stream()
				.map(roleMapper::toDto)
				.collect(Collectors.toList());
	}

}
