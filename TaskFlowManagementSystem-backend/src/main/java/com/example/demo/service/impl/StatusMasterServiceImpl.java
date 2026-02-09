package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.StatusMasterConflictException;
import com.example.demo.exception.StatusMasterNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.mapper.StatusMasterMapper;
import com.example.demo.model.dto.StatusMasterDto;
import com.example.demo.model.entity.StatusMaster;
import com.example.demo.model.entity.User;
import com.example.demo.repository.StatusMasterRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.service.StatusMasterService;




@Service
@Transactional
public class StatusMasterServiceImpl implements StatusMasterService {

	@Autowired
	private StatusMasterRepository statusMasterRepository;
	
	@Autowired
	private StatusMasterMapper statusMasterMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void createStatusMaster(CustomUserDetails customUserDetails,StatusMasterDto statusMasterDto) {
		//1.驗證基本
		validateDto(statusMasterDto);
		if (statusMasterDto.getCode() == null) throw new ValidationException("Code cannot be null");
		//2.驗證身分 並丟到 Dto (更新資料用)
		Long isYourId=customUserDetails.getUser().getId();
		User isYou=userRepository.findById(isYourId).orElseThrow(()-> new UserNotFoundException(isYourId));

		statusMasterDto.setActive(true);
		//3.存入(SQL 有規範 這就不用擋了)
		
		StatusMaster statusMasterForSave = statusMasterMapper.toEntity(statusMasterDto);
		statusMasterForSave.setCreatedBy(isYou);
		statusMasterForSave.setUpdatedBy(isYou);
		statusMasterRepository.save(statusMasterForSave);
	}

	@Override
	public void updateStatusMaster(CustomUserDetails customUserDetails,StatusMasterDto statusMasterDto) {
		//1.驗證基本
		validateDto(statusMasterDto);
		if (statusMasterDto.getId() == null) throw new ValidationException("Id cannot be null");
		//2.驗證身分 + 找資料
		Long isYourId=customUserDetails.getUser().getId();
		User isYou=userRepository.findById(isYourId).orElseThrow(()-> new UserNotFoundException(isYourId));
		Long statusMasterId=statusMasterDto.getId();
		StatusMaster existingStatusMaster = statusMasterRepository.findById(statusMasterId).orElseThrow(()-> new StatusMasterNotFoundException(statusMasterId) );

		//3.存入(SQL 有規範 這就不用擋了)
		statusMasterMapper.updateEntity(statusMasterDto,existingStatusMaster);
		existingStatusMaster.setUpdatedBy(isYou);
		statusMasterRepository.save(existingStatusMaster);	
	}

	@Override
	public void deleteStatusMaster(CustomUserDetails customUserDetails,Long id) {
		//1.找資料存存在 並驗證狀態
		StatusMaster statusMaster = statusMasterRepository.findById(id).orElseThrow(()-> new StatusMasterNotFoundException(id) );
		if(statusMaster.getActive()==false) throw new StatusMasterConflictException("StatusMaster already deleted");
		//2.驗證身分 (更新資料用)
		User isYou=customUserDetails.getUser();
		statusMaster.setUpdatedBy(isYou);
		//3.刪除
		statusMaster.setActive(false);
		//4.存入(SQL 有規範 這就不用擋了)
		statusMasterRepository.save(statusMaster);
	}

	@Override
	public void restoreStatusMaster(CustomUserDetails customUserDetails,Long id) {
		//1.找資料存存在 並驗證狀態
		StatusMaster statusMaster = statusMasterRepository.findById(id).orElseThrow(()-> new StatusMasterNotFoundException(id));
		if(statusMaster.getActive()==true) throw new StatusMasterConflictException("StatusMaster already recovered");
		//2.驗證身分 (更新資料用)
		User isYou=customUserDetails.getUser();
		statusMaster.setUpdatedBy(isYou);
		//3.刪除
		statusMaster.setActive(true);
		//4.存入(SQL 有規範 這就不用擋了)
		statusMasterRepository.save(statusMaster);
	}

	@Override
	public List<StatusMasterDto> searchAllStatusMaster() {
		return statusMasterRepository.findByActiveTrue().stream()
				.map(statusMasterMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public StatusMasterDto searchStatusMaster(Long id) {
		return statusMasterMapper.toDto(statusMasterRepository.findByIdAndActiveTrue(id).orElseThrow(()-> new StatusMasterNotFoundException(id)));
	}
	
	
	
	@Override
	public List<StatusMasterDto> searchAllStatusMasterAdminVer() {
		return statusMasterRepository.findAll().stream()
				.map(statusMasterMapper::toDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public StatusMasterDto searchStatusMasterAdminVer(Long id) {
		return statusMasterMapper.toDto(statusMasterRepository.findById(id).orElseThrow(()-> new StatusMasterNotFoundException(id)));
	}
	
	// 將重複的校驗邏輯抽出成私有方法
    private void validateDto(StatusMasterDto statusMasterDto) {
        if (statusMasterDto.getDescription() == null) throw new ValidationException("Description cannot be null");
        if (statusMasterDto.getLabel() == null) throw new ValidationException("Label cannot be null");
        
    }

}
