package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.model.dto.ApiResponse;



//@ControllerAdvice 處裡非json才用這
@RestControllerAdvice
public class GlobalExceptionHandler {

	 // 處理自定義的用戶找不到
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 2026 建議用 404
                .body(ApiResponse.error(ex.getMessage()));
    }

//    //  處理登入密碼錯誤 (Spring Security 拋出)
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
//        return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED) // 401
//                .body(ApiResponse.error("帳號或密碼錯誤"));
//    }

//    //  處理權限不足 (Spring Security 拋出)
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN) // 403
//                .body(ApiResponse.error("權限不足，無法存取該資源"));
//    }

    //  兜底處理：處理所有未預期的系統錯誤
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleOther(Exception ex) {
        // 在控制台印出詳細錯誤，方便 2026 開發者 Debug
        ex.printStackTrace(); 
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                .body(ApiResponse.error("內部伺服器錯誤"));
    }
	
	
}

