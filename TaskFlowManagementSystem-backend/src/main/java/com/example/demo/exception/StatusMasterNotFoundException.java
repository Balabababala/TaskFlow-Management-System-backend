package com.example.demo.exception;

public class StatusMasterNotFoundException extends StatusMasterException {

    public StatusMasterNotFoundException(Long id) {
        super("StatusMaster not found: " + id);
    }
    public StatusMasterNotFoundException(String code) {
        super("StatusMasterCode not found: " + code);
    }
    
}
