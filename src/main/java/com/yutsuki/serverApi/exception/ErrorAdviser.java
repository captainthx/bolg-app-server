package com.yutsuki.serverApi.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ErrorAdviser {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> msg = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            msg.put(error.getField(), error.getDefaultMessage());
        });
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = HttpStatus.BAD_REQUEST.value();
        errorResponse.error = msg.toString();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = HttpStatus.EXPECTATION_FAILED.value();
        errorResponse.error = e.getMessage();
        return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<?> handleJwtValidationException(JwtValidationException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = HttpStatus.UNAUTHORIZED.value();
        errorResponse.error = e.getMessage();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @Data
    private static class ErrorResponse{
        private int status;
        private String error;
        private LocalDateTime time = LocalDateTime.now();
    }

}
