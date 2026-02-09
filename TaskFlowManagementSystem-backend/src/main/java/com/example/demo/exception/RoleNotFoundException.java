package com.example.demo.exception;

public class RoleNotFoundException extends RoleException {

    public RoleNotFoundException(Integer id) {
        super("Role not found: " + id);
    }
}
