package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.entity.StatusMaster;
import com.example.demo.model.entity.User;

public interface StatusMasterRepository extends JpaRepository<StatusMaster, Long>{
	 // 查狀態代碼
    Optional<StatusMaster> findByCode(String code);

    // 查顯示名稱
    Optional<StatusMaster> findByLabel(String label);
}
