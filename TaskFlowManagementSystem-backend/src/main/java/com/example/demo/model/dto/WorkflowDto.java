package com.example.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WorkflowDto {
	  	private Long id;                    // Workflow 唯一識別
	    private String name;                // 流程名稱
	    private Integer version;            // 版本號
	    private LocalDateTime createdAt;    // 建立時間
	    private LocalDateTime updatedAt;    // 建立時間
	    private Long createdBy;             // 建立者 userId
}
