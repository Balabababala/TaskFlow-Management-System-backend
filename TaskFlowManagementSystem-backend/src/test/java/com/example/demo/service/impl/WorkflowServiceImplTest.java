package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.service.WorkflowService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WorkflowServiceImplTest {

    @Autowired
    private WorkflowService workflowService;  // 注入 Service

    @Test
    void testCreateAndFindAllWorkflow() {
        // 1. 建立 DTO
    	
        WorkflowDto dto = new WorkflowDto();
        dto.setId(1L);
        dto.setName("Test Workflow");
        dto.setVersion(1);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setCreatedBy(1L); // 假設 user id 1 存在資料庫
        /*
        // 2. 建立 workflow
        workflowService.createWorkflow(dto);
		
        // 3. 讀取所有 workflow
        List<WorkflowDto> allWorkflows = workflowService.findAllWorkflow();

        // 4. 驗證
        assertFalse(allWorkflows.isEmpty());
        assertTrue(allWorkflows.stream().anyMatch(w -> "Test Workflow".equals(w.getName())));
       
        // 輸出查看
        allWorkflows.forEach(System.out::println);
        */
    }
}
