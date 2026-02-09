package com.example.demo.exception;

public class ValidationException extends TaskflowException {

    public ValidationException() {
		super();
	}
	
	public ValidationException(String message) {
		super(message);
	}
	public ValidationException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
