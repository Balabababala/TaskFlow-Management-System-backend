//package com.example.demo.service.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.exception.StatusMasterNotFoundException;
//import com.example.demo.exception.ValidationException;
//import com.example.demo.exception.WorkflowNotFoundException;
//import com.example.demo.model.entity.Status;
//import com.example.demo.model.entity.StatusMaster;
//import com.example.demo.model.entity.Workflow;
//import com.example.demo.repository.StatusMasterRepository;
//import com.example.demo.repository.WorkflowRepository;
//import com.example.demo.service.StatusService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//
//@Service
//public class StatusServiceImpl implements StatusService {
//	
//	
//	// 在 Service 裡
//	private final ObjectMapper objectMapper = new ObjectMapper();
//	
//	@Autowired
//	private WorkflowRepository workflowRepository;
//	@Autowired
//	private StatusMasterRepository statusMasterRepository;
//	
//	public Long createStatus(Long workflowId,Long masterId,List<String>allowedTransitions) {
//		//1.是否為null
//		if (allowedTransitions == null || allowedTransitions.isEmpty())throw new ValidationException("Transitions list is empty");
//		//2.是否存在 可能不需要 寫workflow 時再確認
//		Workflow workflow= workflowRepository.findById(workflowId).orElseThrow(()->new WorkflowNotFoundException(workflowId));
//		StatusMaster statusMaster= statusMasterRepository.findById(masterId).orElseThrow(()->new StatusMasterNotFoundException(masterId));
//		allowedTransitions.stream().
//			forEach((code)->statusMasterRepository.findByCode(code).orElseThrow(()->new StatusMasterNotFoundException(code)).toString());
//		//3.儲存
//		Status status = new Status();
//	    status.setWorkflow(workflow);
//	    status.setStatusMaster(statusMaster);
//	    // 將 List<String> 序列化成 JSON 字串: ["A", "B"]
//	    try { 
//	        String jsonString = objectMapper.writeValueAsString(allowedTransitions);
//	        status.setAllowedTransitions(jsonString); // 存入 Entity 的 String 欄位
//	        
//	    } catch (JsonProcessingException e) {
//	        throw new RuntimeException("JSON 轉換失敗", e);
//	    }
//	   
//		return 0L;
//	}; 
//}