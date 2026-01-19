package com.example.demo.service;

import com.example.demo.model.dto.LoginRequest;

public interface AuthService {
	String authenticateUser(LoginRequest loginRequest) ;
}
