package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.RoleNotMatchException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.WorkflowMapper;
import com.example.demo.model.dto.ApiResponse;
import com.example.demo.model.dto.StatusMasterDto;
import com.example.demo.model.dto.WorkflowDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.StatusMasterService;
import com.example.demo.service.WorkflowService;

@RestController
@RequestMapping("/api/admin/statusMaster")
public class AdminStatusMasterController {

	@Autowired
	private StatusMasterService statusMasterService; 

	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Void>> createStatusMaster(@AuthenticationPrincipal CustomUserDetails customUserDetails , @RequestBody StatusMasterDto statusMasterDto){
	    
		statusMasterService.createStatusMaster(customUserDetails,statusMasterDto);
	    return ResponseEntity.ok(ApiResponse.success("創建成功", null));

	}
	
	
	
	@PostMapping("/update")
	public ResponseEntity<ApiResponse<Void>> updateStatusMaster(@AuthenticationPrincipal CustomUserDetails customUserDetails ,@RequestBody StatusMasterDto statusMasterDto){

	    
		statusMasterService.updateStatusMaster(customUserDetails,statusMasterDto);
	    return ResponseEntity.ok(ApiResponse.success("更改成功", null));
	}
	

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteStatusMaster(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id){
	    
		statusMasterService.deleteStatusMaster(customUserDetails ,id);
	    return ResponseEntity.ok(ApiResponse.success("刪除成功", null));
	}
	
	
	@PostMapping("/restore/{id}")
	public ResponseEntity<ApiResponse<Void>> restoreStatusMaster(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long id){
	    
		statusMasterService.restoreStatusMaster(customUserDetails ,id);
	    return ResponseEntity.ok(ApiResponse.success("回復成功", null));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<StatusMasterDto>> searchStatusMaster(@PathVariable Long id){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", statusMasterService.searchStatusMasterAdminVer(id)));
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<StatusMasterDto>>> searchAllStatusMaster(){
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", statusMasterService.searchAllStatusMasterAdminVer()));
	}
	
	

}
