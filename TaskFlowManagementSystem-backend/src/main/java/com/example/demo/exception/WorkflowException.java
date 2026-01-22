package com.example.demo.exception;

public class WorkflowException extends TaskflowException{

	public WorkflowException() {
		super();
	}
	
	public WorkflowException(String message) {
		super(message);
	}
	public WorkflowException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
