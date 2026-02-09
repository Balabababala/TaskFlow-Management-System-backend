package com.example.demo.exception;

public class WorkflowConflictException extends WorkflowException {

    public WorkflowConflictException(String conflict) {
        super("Workflow Conflict: " + conflict);
    }
    
}
