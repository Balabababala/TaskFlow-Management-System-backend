package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.entity.TaskHistory;



public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
	
	
//	 @Query(value = "SELECT th.id AS th_id, th.timestamp, " +
//             "t.id AS task_id, t.title, " +
//             "fs.id AS from_status_id, fs.code AS from_status_code, " +
//             "ts.id AS to_status_id, ts.code AS to_status_code, " +
//             "u.id AS changed_by_id, u.username " +
//             "FROM task_history th " +
//             "JOIN task t ON th.task_id = t.id " +
//             "JOIN status fs ON th.from_status_id = fs.id " +
//             "JOIN status ts ON th.to_status_id = ts.id " +
//             "JOIN users u ON th.changed_by = u.id " +
//             "WHERE t.id = :taskId " +
//             "ORDER BY th.timestamp ASC",
//     nativeQuery = true)
//	 List<Optional<TaskHistory>> findTaskHistoryWithRelations(@Param("taskId") Long taskId);
	 
	 @EntityGraph(attributePaths = {"task", "fromStatus", "toStatus", "changedBy"})
	 List<TaskHistory> findByTaskIdOrderByTimestampAsc(Long taskId);
}
