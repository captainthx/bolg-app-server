package com.yutsuki.serverApi.common;

import com.yutsuki.serverApi.utils.model.PaginationResponse;
import com.yutsuki.serverApi.utils.model.Result;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class ResponseUtil implements Serializable {

    public static ResponseEntity<?> success() {
        return success(null);
    }

    public static ResponseEntity<?> successEmpty() {
        return success(Collections.EMPTY_LIST);
    }

    public static <E> ResponseEntity<?> success(E result) {
        Result<?> response = Result.builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .result(result)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T, E> ResponseEntity<?> successList(Page<T> page, E result) {
        PaginationResponse paginate = PaginationResponse.builder()
                .limit(page.getPageable().getPageSize())
                .current(page.getPageable().getPageNumber() + 1)
                .records((int) page.getTotalElements())
                .pages(page.getTotalPages())
                .build();

        Result<?> response = Result.builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .result(result)
                .pagination(paginate)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<?> successList(Page<T> result) {
        PaginationResponse paginate = PaginationResponse.builder()
                .limit(result.getPageable().getPageSize())
                .current(result.getPageable().getPageNumber() + 1)
                .records((int) result.getTotalElements())
                .pages(result.getTotalPages())
                .build();

        Result<?> response = Result.builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .result(result)
                .pagination(paginate)
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<?> error(ResponseCode code, String message) {
        Result<?> res = Result.builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .code(code.getMapping())
                .message(message)
                .build();
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> error(Integer code, String message) {
        Result<?> res = Result.builder()
                .status(HttpStatus.EXPECTATION_FAILED.getReasonPhrase())
                .code(code)
                .message(message)
                .build();
        return new ResponseEntity<>(res, HttpStatus.EXPECTATION_FAILED);
    }

    public static ResponseEntity<?> error(Integer code, Map<String, String> message) {
        Result<?> res = Result.builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .code(code)
                .errors(message)
                .build();
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> unknown() {
        Result<?> res = Result.builder()
                .code(HttpStatus.EXPECTATION_FAILED.value())
                .message(HttpStatus.EXPECTATION_FAILED.getReasonPhrase())
                .build();
        return new ResponseEntity<>(res, HttpStatus.EXPECTATION_FAILED);
    }

    public static ResponseEntity<?> unauthorized() {
        Result<?> res = Result.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .build();
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }
    public static ResponseEntity<?> forbidden() {
        Result<?> res = Result.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                .build();
        return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
    }


}
