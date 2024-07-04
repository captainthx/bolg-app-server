package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.ApiProperties;
import com.yutsuki.serverApi.common.EmailProperties;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.email.SendMailService;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.AccountException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.EmailException;
import com.yutsuki.serverApi.model.request.ForgotPasswordRequest;
import com.yutsuki.serverApi.model.request.ResetPasswordRequest;
import com.yutsuki.serverApi.repository.AccountRepository;
import com.yutsuki.serverApi.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ForgotPasswordService {
    @Resource
    private RedisService redisService;
    @Resource
    SendMailService sendMailService;
    @Resource
    private EmailProperties emailProperties;
    @Resource
    private AccountRepository accountRepository;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    private ApiProperties apiProperties;


    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest request) throws BaseException {
        if (ValidateUtil.invalidEmail(request.getEmail())) {
            log.warn("ForgotPassword::(block).invalid email. {}", request);
            throw EmailException.invalidEmail();
        }
        String resetToken = UUID.randomUUID().toString();
        // save resetToken to redis
        TimeUnit timeUnit = TimeUnit.MINUTES;

        redisService.set(request.getUsername(), resetToken, apiProperties.getResetTokenExpire(), timeUnit);

        String link = emailProperties.getResetPasswordUrl() + resetToken + "&username=" + request.getUsername();
        sendMailService.sendResetPassword(request.getEmail(), link);
        return ResponseUtil.success();
    }

    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) throws BaseException {
        Optional<Account> accountOptional = accountRepository.findByUserName(request.getUsername());
        if (!accountOptional.isPresent()) {
            log.warn("ResetPassword::(block).account not found. {}", request);
            throw AccountException.accountNotFound();
        }
        Account account = accountOptional.get();
        if (redisService.isExpiredKey(request.getUsername())) {
            log.warn("ResetPassword::(block).reset password token expired. {}", request);
            throw AccountException.resetPasswordTokenExpired();
        }
        Object redisObj = redisService.get(request.getUsername());
        if (!redisObj.equals(request.getToken())) {
            log.warn("ResetPassword::(block).reset password token invalid. {}", request);
            throw AccountException.resetPasswordTokenInvalid();
        }
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(account);
        redisService.deleteKey(request.getUsername());
        return ResponseUtil.success();
    }

}
