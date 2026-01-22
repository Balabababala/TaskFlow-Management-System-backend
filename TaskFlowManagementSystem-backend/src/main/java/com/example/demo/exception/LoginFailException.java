package com.example.demo.exception;

public class LoginFailException extends LoginException {
    public LoginFailException(String message) {
        super(message);
    }
}