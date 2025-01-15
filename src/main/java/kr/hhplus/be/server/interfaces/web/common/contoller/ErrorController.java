package kr.hhplus.be.server.interfaces.web.common.contoller;

import kr.hhplus.be.server.interfaces.web.common.dto.ApiResponse;
import kr.hhplus.be.server.support.ErrorCode;
import kr.hhplus.be.server.support.exception.CommonException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalStateException(CommonException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(ApiResponse.error(e.getMessage()));
    }
}
