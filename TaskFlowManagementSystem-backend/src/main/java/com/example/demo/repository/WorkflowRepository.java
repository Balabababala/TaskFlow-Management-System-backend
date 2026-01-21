package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.entity.Workflow;



public interface WorkflowRepository extends JpaRepository<Workflow, Long>{
	 // 查某個流程模板的所有版本
    List<Workflow> findByNameOrderByVersionDesc(String name);

    // 找最新版本
    Optional<Workflow> findFirstByNameOrderByVersionDesc(String name);
    
//	  查 workflow 及建立者 他等於下面那行
//  @Query(value = "SELECT w.id AS workflow_id, w.name, w.version, w.created_at, " +
//          "u.id AS creator_id, u.username, u.email " +
//          "FROM workflow w " +
//          "JOIN users u ON w.created_by = u.id " +
//          "WHERE w.id = :workflowId",
//  nativeQuery = true)
//  Optional<Workflow> findWorkflowWithCreatorById(@Param("workflowId") Long workflowId);
    @Override
    @EntityGraph(attributePaths = {"createdBy"})
    Optional<Workflow> findById (Long id);
    
    @Override
    @EntityGraph(attributePaths = {"createdBy"})
    List <Workflow> findAll ();
    
    // 只找啟用的
    @EntityGraph(attributePaths = {"createdBy"})
    List<Workflow> findByActiveTrue();
    
    // 只找特定的啟用流程
    @EntityGraph(attributePaths = {"createdBy"})
    Optional<Workflow> findByIdAndActiveTrue(Long id);
    
	boolean existsByNameAndVersion(String name, Integer version);

}
