package com.example.demo.exception;

public class TaskConflictException extends TaskException {

    public TaskConflictException(String conflict) {
        super("Task Conflict: "+conflict);
    }
}
