package com.example.demo.exception;

public class TaskAssigneeException extends TaskflowException {

    public TaskAssigneeException() {
		super();
	}
	
	public TaskAssigneeException(String message) {
		super(message);
	}
	public TaskAssigneeException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
