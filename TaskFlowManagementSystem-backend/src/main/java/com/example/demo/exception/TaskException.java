package com.example.demo.exception;

public class TaskException extends TaskflowException {

    public TaskException() {
		super();
	}
	
	public TaskException(String message) {
		super(message);
	}
	public TaskException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
