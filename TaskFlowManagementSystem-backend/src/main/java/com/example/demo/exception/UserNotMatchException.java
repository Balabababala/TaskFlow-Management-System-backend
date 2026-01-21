package com.example.demo.exception;

public class UserNotMatchException extends TaskflowException {

    public UserNotMatchException(String id) {
        super("User not match: " + id);
    }
}
