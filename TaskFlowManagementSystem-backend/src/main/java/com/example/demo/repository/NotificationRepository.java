package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.entity.Notification;
import com.example.demo.model.entity.TaskAssignee;



public interface NotificationRepository extends JpaRepository<Notification, Long>{
	 // 查某任務所有被指派者
    List<TaskAssignee> findByTaskId(Long taskId);

    // 查某使用者被指派的任務
    List<TaskAssignee> findByUserId(Long userId);

    // 查某使用者負責的某個任務節點
    @Query(value = "SELECT n.id AS notification_id, n.message, n.read_flag, n.created_at, " +
            "u.id AS user_id, u.username, " +
            "t.id AS task_id, t.title " +
            "FROM notification n " +
            "JOIN users u ON n.user_id = u.id " +
            "LEFT JOIN task t ON n.task_id = t.id " +
            "WHERE n.user_id = :userId AND n.read_flag = false",
    nativeQuery = true)
    List<Object[]> findUnreadNotificationWithRelations(@Param("userId") Integer userId);
}
