package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.StatusMasterDto;

import com.example.demo.secure.CustomUserDetails;



public interface StatusMasterService {
	 void createStatusMaster (CustomUserDetails customUserDetails,StatusMasterDto statusMasterDto);
	
	 void updateStatusMaster (CustomUserDetails customUserDetails,StatusMasterDto statusMasterDto);
	
	 void deleteStatusMaster (CustomUserDetails customUserDetails,Long id);
	
	 void restoreStatusMaster (CustomUserDetails customUserDetails,Long id);
	
	 List<StatusMasterDto> searchAllStatusMaster ();
	 StatusMasterDto searchStatusMaster(Long id);
	 List<StatusMasterDto> searchAllStatusMasterAdminVer ();
	 StatusMasterDto searchStatusMasterAdminVer(Long id);
}
