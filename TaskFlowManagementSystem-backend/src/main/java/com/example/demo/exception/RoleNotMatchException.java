package com.example.demo.exception;

public class RoleNotMatchException extends TaskflowException {

    public RoleNotMatchException(String role) {
        super("Role not match: " + role);
    }
}
