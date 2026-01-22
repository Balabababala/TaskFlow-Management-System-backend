package com.example.demo.exception;

public class RoleException extends TaskflowException {

    public RoleException() {
		super();
	}
	
	public RoleException(String message) {
		super(message);
	}
	public RoleException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
