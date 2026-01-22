package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.StatusMasterNotFoundException;
import com.example.demo.exception.WorkflowNotFoundException;
import com.example.demo.model.entity.Status;
import com.example.demo.model.entity.StatusMaster;
import com.example.demo.model.entity.Workflow;
import com.example.demo.repository.StatusMasterRepository;
import com.example.demo.repository.WorkflowRepository;
import com.example.demo.service.StatusService;



@Service
public class StatusServiceImpl implements StatusService {
	
	@Autowired
	private WorkflowRepository workflowRepository;
	@Autowired
	private StatusMasterRepository statusMasterRepository;
	
	public Long createStatus(Long workflowId,Long masterId,List<String>allowedTransitions) {
		//1.是否為null
		if (allowedTransitions == null || allowedTransitions.isEmpty())throw new IllegalArgumentException("Transitions list is empty");
		//2.是否存在 可能不需要 寫workflow 時再確認
		Workflow workflow= workflowRepository.findById(workflowId).orElseThrow(()->new WorkflowNotFoundException(workflowId));
		StatusMaster statusMaster= statusMasterRepository.findById(masterId).orElseThrow(()->new StatusMasterNotFoundException(masterId));
		allowedTransitions.stream().
			forEach((code)->statusMasterRepository.findByCode(code).orElseThrow(()->new StatusMasterNotFoundException(code)).toString());
		//3.儲存
		Status status = new Status();
	    status.setWorkflow(workflow);
	    status.setStatusMaster(statusMaster);
		
		
		return 0L;
	}; 
}
