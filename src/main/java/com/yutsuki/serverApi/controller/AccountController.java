package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.UpdAccountRequest;
import com.yutsuki.serverApi.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping("/profile")
    public ResponseEntity<?> findAccount() throws BaseException {
        return this.accountService.findById();
    }
    @PatchMapping()
    public ResponseEntity<?>uploadAvatar(@RequestBody UpdAccountRequest request) throws BaseException {
        return this.accountService.updateAccount(request);
    }
}


