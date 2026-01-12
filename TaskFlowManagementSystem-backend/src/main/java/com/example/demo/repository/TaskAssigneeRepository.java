package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.TaskAssignee;

public interface TaskAssigneeRepository extends JpaRepository<TaskAssignee, Long>{

}
