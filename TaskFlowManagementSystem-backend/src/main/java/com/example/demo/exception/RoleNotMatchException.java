package com.example.demo.exception;

public class RoleNotMatchException extends RoleException {

    public RoleNotMatchException(String role) {
        super("Role not match: " + role);
    }
}
