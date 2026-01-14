package com.example.demo.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.model.dto.ApiResponse;



// 利用 @ControllerAdvice 的特性來處理全局錯誤
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(
            UserNotFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }
	
	@ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(
	            AccessDeniedException ex) {
	        return ResponseEntity
	                .status(403)
	                .body(ApiResponse.error(ex.getMessage()));
	    }
	 
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleOther(Exception ex) {

        return ResponseEntity
                .status(500)
                .body(ApiResponse.error("internal server error"));
    }
}

