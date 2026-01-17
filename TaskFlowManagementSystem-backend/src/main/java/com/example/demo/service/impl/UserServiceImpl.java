package com.example.demo.service.impl;
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired 
	private  UserRepository userRepository;
	@Autowired
	private  UserMapper userMapper;
	@Override
	public void createUser(UserDto userDto) {
		// 1. 基礎校驗
		validateCreateDto(userDto);
		boolean exists = userRepository.existsByUsername(userDto.getUsername());
		// 2. 檢查重複
		if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
		
		User user = userMapper.toEntity(userDto);
		// 等SpringSecure 補好 AAA相關 
		userRepository.save(user);
		
	}

	@Override
	public void updateUser(UserDto userDto) {
		// 1. 基礎校驗
		validateCreateDto(userDto);
		boolean exists = userRepository.existsByUsername(userDto.getUsername());
		
		//2. 記錄舊資料
		User existingUser = userRepository.findById(userDto.getId())
	            .orElseThrow(() -> new UserNotFoundException(userDto.getId()));
		
		userMapper.updateEntity(userDto,existingUser);
		// 等SpringSecure 補好 AAA相關 
		userRepository.save(existingUser);
		

		// 等SpringSecure 補好 AAA相關 
		
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreUser(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WorkflowDto findUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkflowDto> findAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	// 將重複的校驗邏輯抽出成私有方法
    private void validateLoginDto(UserDto userDto) {
        if (userDto.getUsername() == null) throw new IllegalArgumentException("Username cannot be null");
        if (userDto.getPassword() == null) throw new IllegalArgumentException("Password cannot be null");
    }
    private void validateCreateDto(UserDto userDto) {
    	validateLoginDto(userDto);
    	if (userDto.getEmail() == null) throw new IllegalArgumentException("Email cannot be null");
        if (userDto.getFullName() == null) throw new IllegalArgumentException("Full Name cannot be null");
        
    }
    

}
