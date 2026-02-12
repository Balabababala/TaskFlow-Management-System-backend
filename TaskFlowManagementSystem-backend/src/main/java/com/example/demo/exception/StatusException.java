package com.example.demo.exception;

public class StatusException extends TaskflowException{

	public StatusException() {
		super();
	}
	
	public StatusException(String message) {
		super(message);
	}
	public StatusException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
