package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.entity.Task;
import com.example.demo.model.entity.TaskAssignee;



public interface TaskAssigneeRepository extends JpaRepository<TaskAssignee, Long>{
	 // 查某任務所有被指派者
    List<TaskAssignee> findByTaskId(Long taskId);

    // 查某使用者被指派的任務
    List<TaskAssignee> findByUserId(Long userId);

    // 查某使用者負責的某個任務節點
    @Query(value = "SELECT ta.id AS ta_id, ta.assigned_at, " +
            "t.id AS task_id, t.title, " +
            "u.id AS user_id, u.username, " +
            "s.id AS status_id, s.code AS status_code " +
            "FROM task_assignee ta " +
            "JOIN task t ON ta.task_id = t.id " +
            "JOIN users u ON ta.user_id = u.id " +
            "JOIN status s ON ta.status_id = s.id " +
            "WHERE t.id = :taskId",
    nativeQuery = true)
    List<Object[]> findTaskAssigneeWithRelations(@Param("taskId") Long taskId);
}
