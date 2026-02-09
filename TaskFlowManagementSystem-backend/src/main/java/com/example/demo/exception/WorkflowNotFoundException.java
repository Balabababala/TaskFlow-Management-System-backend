package com.example.demo.exception;

public class WorkflowNotFoundException extends WorkflowException {

    public WorkflowNotFoundException(Long id) {
        super("Workflow is not found: " + id);
    }
    
}
