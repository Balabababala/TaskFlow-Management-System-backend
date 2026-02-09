package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.model.dto.ApiResponse;



//@ControllerAdvice 處裡非json才用這
@RestControllerAdvice
public class GlobalExceptionHandler {

//Spring Security
    //  處理登入密碼錯誤 (Spring Security 拋出)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401
                .body(ApiResponse.error("帳號或密碼錯誤"));
    }

    //  處理權限不足 (Spring Security 拋出)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN) // 403
                .body(ApiResponse.error("權限不足，無法存取該資源"));
    }

    
//自己的   
      
    @ExceptionHandler({RoleNotFoundException.class,
    					StatusMasterNotFoundException.class,
    					UserNotFoundException.class,
    					WorkflowNotFoundException.class})
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(Exception ex) {

        ex.printStackTrace(); 
        return ResponseEntity
        		.status(HttpStatus.NOT_FOUND)  // 404:
                .body(ApiResponse.error(ex.getMessage()));
    }  
    
    // 
    @ExceptionHandler({ValidationException.class,
    					RoleNotMatchException.class,
    					UserNotMatchException.class})
    public ResponseEntity<ApiResponse<Void>> handleValidationException(Exception ex) {

        ex.printStackTrace(); 
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400: 使用者請求錯誤
                .body(ApiResponse.error(ex.getMessage()));
    }  
    
    @ExceptionHandler({WorkflowConflictException.class,
    	UserConflictException.class,
    	StatusMasterConflictException.class})
	public ResponseEntity<ApiResponse<Void>> handleConflictException(Exception ex) {

	ex.printStackTrace(); 
	return ResponseEntity
	.status(HttpStatus.CONFLICT) // 409: 
	.body(ApiResponse.error(ex.getMessage()));
	}  
    
    
    
    //LoginFailException 401
    
    // 處理資料庫唯一約束衝突（如：名稱+版本重複）就是資料庫設 unique 然後有重複 就跑這 
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex) {
        // 獲取最具體的錯誤訊息
        String msg = ex.getMostSpecificCause().getMessage();
        
        // 根據你資料庫設定的約束名稱進行判斷 (uc_workflow_name_version)
        if (msg != null && msg.contains("uc_workflow_name_version")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409 Conflict
                    .body(ApiResponse.error("該名稱與版本的組合已存在"));
        }
        
        // 其他資料完整性錯誤（如：外鍵約束、欄位長度過長）
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(ApiResponse.error("資料存儲異常：請檢查數據是否重複或符合規範"));
    }
    
    // 自己的 兜底處理：處理所有未預期的系統錯誤
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleOther(Exception ex) {
        // 在控制台印出詳細錯誤，方便 2026 開發者 Debug
        ex.printStackTrace(); 
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                .body(ApiResponse.error("內部伺服器錯誤"));
    }
	
    //JsonProcessingException
    //todo
}

