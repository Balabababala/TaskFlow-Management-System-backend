package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.TaskHistory;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

}
