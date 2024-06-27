package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.AuthException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Slf4j
public class SecurityService {
    @Resource
    private TokenService tokenService;
    @Resource
    private AccountRepository accountRepository;

    public Account getUserDetail() throws BaseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        if (!authentication.isAuthenticated()) {
            log.warn("GetUserDetail::(block). User is not authenticated. {}", jwt.getTokenValue());
            throw AuthException.unauthenticated();
        }
        if (!tokenService.validates(jwt.getTokenValue())) {
            log.warn("GetUserDetail::(block). Token is invalid. {}", jwt.getTokenValue());
            throw AuthException.tokenUnauthorized();
        }
        Long userId = tokenService.getUserId(jwt.getTokenValue());
        Optional<Account> account = accountRepository.findById(userId);
        if (!account.isPresent()) {
            log.warn("GetUserDetail::(block). Account not found. {}", jwt.getTokenValue());
            throw AuthException.accountNotFound();
        }
        return account.get();
    }
}
