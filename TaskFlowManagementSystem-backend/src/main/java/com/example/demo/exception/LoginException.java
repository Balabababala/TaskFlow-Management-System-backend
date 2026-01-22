package com.example.demo.exception;

public class LoginException extends TaskflowException {

    public LoginException() {
		super();
	}
	
	public LoginException(String message) {
		super(message);
	}
	public LoginException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
