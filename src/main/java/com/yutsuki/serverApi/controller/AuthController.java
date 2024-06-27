package com.yutsuki.serverApi.controller;


import com.yutsuki.serverApi.exception.AuthException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.AuthLoginRequest;
import com.yutsuki.serverApi.model.request.AuthRefreshTokenRequest;
import com.yutsuki.serverApi.model.request.AuthSignupRequest;
import com.yutsuki.serverApi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthSignupRequest request) throws BaseException {
        return this.authService.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest request) throws BaseException {
        return this.authService.login(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AuthRefreshTokenRequest request) throws AuthException {
        return this.authService.refreshToken(request);
    }
}

