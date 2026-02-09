package com.example.demo.exception;

public class UserConflictException extends UserException {

    public UserConflictException(String conflict) {
        super("User Conflict: "+conflict);
    }
}
