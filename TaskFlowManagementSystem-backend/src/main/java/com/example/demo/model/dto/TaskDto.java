package com.example.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private Long workflowId;
    private Long statusId;
    private Long ownerId;
    
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Boolean active;

}