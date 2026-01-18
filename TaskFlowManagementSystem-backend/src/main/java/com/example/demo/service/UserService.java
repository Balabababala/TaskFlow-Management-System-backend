package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.WorkflowDto;

public interface UserService {
	 void createUser(UserDto UserDto);
	
	 void updateUser(UserDto UserDto);
	
	 void deleteUser(Long id);
	
	 void restoreUser(Long id);
	
	 UserDto findUser(Long id);
	 List<UserDto> findAllUser();
}
