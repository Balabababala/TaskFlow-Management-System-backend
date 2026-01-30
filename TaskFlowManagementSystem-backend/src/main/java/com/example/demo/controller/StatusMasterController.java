package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.dto.ApiResponse;
import com.example.demo.model.dto.StatusMasterDto;

import com.example.demo.repository.UserRepository;

import com.example.demo.service.StatusMasterService;


@RestController
@RequestMapping("/api/statusMaster")
public class StatusMasterController {

	@Autowired
	private StatusMasterService statusMasterService; 

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<StatusMasterDto>> searchStatusMaster(@PathVariable Long id){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", statusMasterService.searchStatusMasterAdminVer(id)));
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<StatusMasterDto>>> searchAllStatusMaster(){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", statusMasterService.searchAllStatusMasterAdminVer()));
	}
}
