package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.LoginRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.secure.JwtService;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private UserMapper userMapper;
	
	@Override
	public String authenticateUser(LoginRequest loginRequest) {
	    // 1. 驗證身分 (若密碼錯會在此拋出異常，不會往下執行)
	    authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            loginRequest.getUsername(),
	            loginRequest.getPassword()
	        )
	    );

	    // 2. 驗證成功後，轉換為 DTO 並產生 Token 回傳
	    // 注意：orElseThrow 必須加上 () -> 
	    return jwtService.generateJwtToken(
	        userMapper.toDto(
	            userRepository.findByUsername(loginRequest.getUsername())
	                .orElseThrow(() -> new UserNotFoundException(loginRequest.getUsername()))
	        )
	    );
	}
}
