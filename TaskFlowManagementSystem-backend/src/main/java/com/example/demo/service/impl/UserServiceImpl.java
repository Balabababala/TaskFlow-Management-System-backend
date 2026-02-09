package com.example.demo.service.impl;
 

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserConflictException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import jakarta.transaction.Transactional;

@Transactional
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
            throw new UserConflictException("Username already exists");
        }
		
		User user = userMapper.toEntity(userDto);
		// 等SpringSecure 補好 AAA相關 
		userRepository.save(user);
		
	}

	@Override
	public void updateUser(UserDto userDto) {
		// 1. 基礎校驗
		validateCreateDto(userDto);
		
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
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));		
		if (!user.getActive()) {
		    throw new UserConflictException("user already deleted");
		}
		// 等SpringSecure 補好 要加身分檢查
		user.setActive(false);
		userRepository.save(user);
		
	}

	@Override
	public void restoreUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));		
		if (user.getActive()) {
		    throw new UserConflictException("user already active");
		}
		// 等SpringSecure 補好 要加身分檢查
		user.setActive(true);
		userRepository.save(user);
		
	}

	@Override
	public UserDto findUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));	
		
		return userMapper.toDto(user);
	}

	@Override
	public List<UserDto> findAllUser() {
		return userRepository.findAll().stream()
				.map(userMapper::toDto)
				.collect(Collectors.toList());
	}

	// 將重複的校驗邏輯抽出成私有方法
    private void validateLoginDto(UserDto userDto) {
        if (userDto.getUsername() == null) throw new ValidationException("Username cannot be null");
        if (userDto.getPassword() == null) throw new ValidationException("Password cannot be null");
    }
    private void validateCreateDto(UserDto userDto) {
    	validateLoginDto(userDto);
    	if (userDto.getEmail() == null) throw new ValidationException("Email cannot be null");
        if (userDto.getFullName() == null) throw new ValidationException("Full Name cannot be null");
        
    }
    

}
