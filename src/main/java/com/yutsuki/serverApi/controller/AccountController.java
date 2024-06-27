package com.yutsuki.serverApi.controller;

import com.nimbusds.jwt.JWT;
import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.AccountException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.jwt.UserDetailsImp;
import com.yutsuki.serverApi.service.AccountService;
import com.yutsuki.serverApi.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
@RequestMapping("/v1/account")
@Slf4j
public class AccountController {
    @Resource
    private AccountService accountService;


    @GetMapping("/all")
    public ResponseEntity<?> findAllAccount(Pagination pagination) throws BaseException {
        return this.accountService.findAll(pagination);
    }

    @GetMapping("/me")
    public ResponseEntity<?> findAccount() throws BaseException {
        return this.accountService.findById();
    }
}


