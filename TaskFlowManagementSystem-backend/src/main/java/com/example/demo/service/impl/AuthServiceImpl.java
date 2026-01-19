package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.LoginRequest;
import com.example.demo.secure.JwtService;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Override
	public String authenticateUser(LoginRequest loginRequest) {
		// 1. 呼叫 Spring Security 的管理器進行驗證
        // 這會觸發 UserDetailsService 去資料庫抓資料並比對 BCrypt 密碼
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        // 2. 驗證成功，產生 Token
        return jwtService.generateAuthJwtToken(authentication.getName());
	}
}
