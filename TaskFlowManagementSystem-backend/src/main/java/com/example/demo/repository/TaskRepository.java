package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.entity.Task;

import io.lettuce.core.dynamic.annotation.Param;

public interface TaskRepository  extends JpaRepository<Task, Long>{
    // 查某使用者建立的任務
    List<Task> findByOwnerId(Integer ownerId);

    // 查某使用者被指派的任務
    List<Task> findByStatusIdInAndOwnerId(List<Integer> statusIds, Integer ownerId);

    // Task + Status + Owner + Workflow
    @Query(value = "SELECT t.id AS task_id, t.title, t.description, t.created_at, t.updated_at, " +
                   "s.id AS status_id, s.allowed_transitions, " +
                   "u.id AS owner_id, u.username, u.email, " +
                   "w.id AS workflow_id, w.name AS workflow_name, w.version AS workflow_version " +
                   "FROM task t " +
                   "JOIN status s ON t.status_id = s.id " +
                   "JOIN users u ON t.owner_id = u.id " +
                   "JOIN workflow w ON t.workflow_id = w.id " +
                   "WHERE t.id = :taskId",
           nativeQuery = true)
    Object findTaskWithAllRelations(@Param("taskId") Integer taskId);
}
