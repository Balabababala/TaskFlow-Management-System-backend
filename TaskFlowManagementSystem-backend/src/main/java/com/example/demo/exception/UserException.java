package com.example.demo.exception;

public class UserException extends TaskflowException {

    public UserException() {
		super();
	}
	
	public UserException(String message) {
		super(message);
	}
	public UserException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
