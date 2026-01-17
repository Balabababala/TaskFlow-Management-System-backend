package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Workflow;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

public class UserServiceImpl implements UserService{
	
	@Autowired 
	private  UserRepository userRepository;
	@Autowired
	private  UserMapper userMapper;
	@Override
	public void createUser(UserDto userDto) {
		boolean exists = userRepository.existsByUsername(userDto.getUsername());
		
		if (exists) {
		    throw new IllegalArgumentException("Username already exists");
		}
		
		User user = userMapper.dtoToEntity(userDto);
		// 等SpringSecure 補好 AAA相關 
		userRepository.save(user);
		
	}

	@Override
	public void updateUser(UserDto userDto) {
		boolean exists = userRepository.existsByUsername(userDto.getUsername());
		
		if (exists) {
		    throw new IllegalArgumentException("Username already exists");
		}
		
		  // 1️⃣ 先從 DB 拿出 user
	    User user = userRepository.findById(userDto.getId())
	            .orElseThrow(() -> new RuntimeException("workflow not found"));

	    // 2️⃣ 檢查 user 是否重複（排除自己）
	    boolean exists = userRepository.existsByUsername(userDto.getUsername());
		
		if (exists) {
		    throw new IllegalArgumentException("Username already exists");
		}

	    // 3️⃣ 更新欄位

	    // 4️⃣ 儲存
		userDtoRepository.save(user);

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

}
