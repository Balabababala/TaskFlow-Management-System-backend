package com.example.demo.exception;

public class StatusMasterConflictException extends StatusMasterException {

    public StatusMasterConflictException(String conflict) {
        super("StatusMaster conflict: "+ conflict );
    }
    
}
