package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.ForgotPasswordRequest;
import com.yutsuki.serverApi.model.request.ResetPasswordRequest;
import com.yutsuki.serverApi.service.ForgotPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/forgot-password")
@Slf4j
public class ForgotPasswordController {
    @Resource
    private ForgotPasswordService forgotPasswordService;

    @PostMapping
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) throws BaseException {
        return forgotPasswordService.forgotPassword(request);
    }

    @GetMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) throws BaseException {
       return forgotPasswordService.resetPassword(request);
    }
}
