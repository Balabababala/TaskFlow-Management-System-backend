package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.entity.StatusMaster;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Workflow;


//@EntityGraph 用來處理n+1用的 attributePaths 是要多茶的資料
public interface StatusMasterRepository extends JpaRepository<StatusMaster, Long>{
	 // 查狀態代碼
	@EntityGraph(attributePaths = {"createdBy","updatedBy"})
    Optional<StatusMaster> findByCode(String code);

    // 查顯示名稱
    @EntityGraph(attributePaths = {"createdBy","updatedBy"})
    Optional<StatusMaster> findByLabel(String label);

    @EntityGraph(attributePaths = {"createdBy","updatedBy"})
	List<StatusMaster> findByActiveTrue();

	@EntityGraph(attributePaths = {"createdBy","updatedBy"})
	Optional<StatusMaster> findByIdAndActiveIsTrue(Long id);
	
	@Override
    @EntityGraph(attributePaths = {"createdBy","updatedBy"})
    List <StatusMaster> findAll ();
	
	@Override
    @EntityGraph(attributePaths = {"createdBy","updatedBy"})
	Optional <StatusMaster> findById(Long id);
}
