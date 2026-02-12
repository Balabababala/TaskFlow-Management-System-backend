package com.example.demo.exception;

public class TaskNotFoundException extends TaskException {

    public TaskNotFoundException(Long taskId) {
        super("Task not found: " + taskId);
    }

}
