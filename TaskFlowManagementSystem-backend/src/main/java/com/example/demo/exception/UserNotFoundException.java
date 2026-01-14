package com.example.demo.exception;

public class UserNotFoundException extends TaskflowException {

    public UserNotFoundException(Long userId) {
        super("User not found: " + userId);
    }
}
