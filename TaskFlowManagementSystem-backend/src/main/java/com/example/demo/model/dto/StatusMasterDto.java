package com.example.demo.model.dto;


import com.example.demo.model.entity.User;

import lombok.Data;

@Data
public class StatusMasterDto {
	private Long id;
	private String code;
	private String label;
	private String description;
	private User createdBy;
	private User updatedBy;
	private Boolean active;
}
