package com.example.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    
    // 1. 保留 ID 供邏輯使用
    private Integer roleId;
    
    // 2. 💡 增加 RoleName 供 JWT 與前端顯示使用
    private String roleName;
    
    // 3. 💡 增加 Active 供前端判斷 UI 狀態
    private Boolean active;
}