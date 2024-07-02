package com.yutsuki.serverApi.exception;

import com.yutsuki.serverApi.common.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        return ResponseUtil.error(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException e) {
        return ResponseUtil.error(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

}
