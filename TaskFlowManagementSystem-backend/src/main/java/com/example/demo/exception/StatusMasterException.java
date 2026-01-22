package com.example.demo.exception;

public class StatusMasterException extends TaskflowException{

	public StatusMasterException() {
		super();
	}
	
	public StatusMasterException(String message) {
		super(message);
	}
	public StatusMasterException(String message, Throwable cause) {
	      super(message, cause);
	 }
}
