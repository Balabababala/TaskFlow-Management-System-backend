package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.LoginFailException;
import com.example.demo.model.dto.ApiResponse;
import com.example.demo.model.dto.LoginRequest;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 呼叫大腦處理
            String token = authService.authenticateUser(loginRequest);
            
            // 回傳 Token 封裝物件
            return ResponseEntity.ok(ApiResponse.success("登入成功", token));
            
        } catch (LoginFailException  e) {
            // 帳密錯誤會噴這個例外
            return ResponseEntity.badRequest().body(ApiResponse.error("登入失敗: " + e.getMessage()));
        }
    }
}