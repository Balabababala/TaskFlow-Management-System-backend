package com.example.demo.exception;

public class UserNotFoundException extends TaskflowException {

    public UserNotFoundException(Long userId) {
        super("User not found: " + userId);
    }
    public UserNotFoundException(String username) {
        super("User not found: " + username);
    }
}
