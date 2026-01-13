package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.entity.Status;

import io.lettuce.core.dynamic.annotation.Param;


public interface StatusRepository extends JpaRepository<Status, Long>{
	  // 查 workflow 下所有狀態 + master + workflow
    @Query(value = "SELECT s.id AS status_id, s.allowed_transitions, " +
                   "sm.id AS master_id, sm.code AS master_code, sm.label AS master_label, " +
                   "w.id AS workflow_id, w.name AS workflow_name, w.version AS workflow_version " +
                   "FROM status s " +
                   "JOIN status_master sm ON s.master_id = sm.id " +
                   "JOIN workflow w ON s.workflow_id = w.id " +
                   "WHERE s.workflow_id = :workflowId",
           nativeQuery = true)
    List<Object[]> findStatusWithMasterAndWorkflowNative(@Param("workflowId") Long workflowId);
}
