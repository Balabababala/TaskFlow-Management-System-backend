package com.example.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String fullName;
	private String username ;
	private String password;
	private String email ;
	private Integer roleId;
}
