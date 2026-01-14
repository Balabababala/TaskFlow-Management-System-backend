package com.example.demo.exception;

public class TaskflowException extends RuntimeException{

	public TaskflowException() {
		super();
	}
	
	public TaskflowException(String message) {
		super(message);
	}
	public TaskflowException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
