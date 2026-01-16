package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

public class UserMapper {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	
	public UserDto entityToDto(User user){
		  UserDto userDto = new UserDto();
		  userDto.setFullName(user.getFullName());
		  userDto.setUsername(user.getUsername());	
		  userDto.setPassword(user.getPassword());
		  userDto.setEmail(user.getEmail());
		  userDto.setRoleId(user.getRole().getId());
		  return userDto;
	}
	
	public User dtoToEntity(UserDto userDto){
		User user=new User();
		user.setFullName(userDto.getFullName());
		user.setUsername(userDto.getUsername());	
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setRole(roleRepository.findById(userDto.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found")));
		
		return user;
	}
}
