package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.RoleNotMatchException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.WorkflowMapper;
import com.example.demo.model.dto.ApiResponse;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.WorkflowService;

@RestController
@RequestMapping("/api")
public class WorkflowController {

	@Autowired
	private WorkflowService workflowService; 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WorkflowMapper workflowMapper;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Void>> createWorkflow(@RequestBody WorkflowDto workflowDto){
		 // 1️⃣ 取得 user
        User user = userRepository.findById(workflowDto.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(workflowDto.getCreatedBy()));
		
		 // 👉 暫時版權限檢查（沒有 Spring Security）
	    if (!"ADMIN".equals(user.getRole().getRoleName())) {
	        throw new RoleNotMatchException("ADMIN");
	    }
	    
	    workflowService.createWorkflow(workflowDto);
	    return ResponseEntity.ok(ApiResponse.success("創建成功", null));
	}

}
