package com.example.demo.exception;

public class UserNotMatchException extends UserException {

    public UserNotMatchException(Long yourId ,Long id) {
        super(yourId+" is not match "+ id);
    }
}
