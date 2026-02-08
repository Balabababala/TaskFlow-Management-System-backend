package com.example.demo.model.dto;

import lombok.Data;

@Data
public class StatusDto {
	private Long statusMasterId;
	private String allowedTransitions;
}
