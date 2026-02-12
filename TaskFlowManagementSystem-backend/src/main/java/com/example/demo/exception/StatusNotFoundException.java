package com.example.demo.exception;

public class StatusNotFoundException extends StatusException {

    public StatusNotFoundException(Long statusId) {
        super("Status not found: " + statusId);
    }
    public StatusNotFoundException(String message) {
        super(message);
    }
}
