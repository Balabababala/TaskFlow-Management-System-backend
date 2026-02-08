package com.example.demo.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class WorkflowRequest {
	  	private WorkflowDto workflow ; 
	  	private List<StatusDto> statusDtos;
	    
}
