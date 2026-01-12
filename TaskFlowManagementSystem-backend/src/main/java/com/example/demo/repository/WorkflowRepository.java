package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.Workflow;

public interface WorkflowRepository extends JpaRepository<Workflow, Long>{

}
