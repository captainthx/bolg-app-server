package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.AuthException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.jwt.Authentication;
import com.yutsuki.serverApi.jwt.model.TokenResponse;
import com.yutsuki.serverApi.model.request.AuthLoginRequest;
import com.yutsuki.serverApi.model.request.AuthRefreshTokenRequest;
import com.yutsuki.serverApi.model.request.AuthSignupRequest;
import com.yutsuki.serverApi.repository.AccountRepository;
import com.yutsuki.serverApi.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public ResponseEntity<?> signup(AuthSignupRequest request) throws BaseException {

        if (Objects.isNull(request.getName())) {
            log.warn("Signup::(block). Name is null. {}", request);
            throw AuthException.invalidName();
        }
        if (Objects.isNull(request.getMobile())) {
            log.warn("Signup::(block). Mobile is null. {}", request);
            throw AuthException.invalidMobile();
        }
        if (Objects.isNull(request.getUsername())) {
            log.warn("Signup::(block). Username is null. {}", request);
            throw AuthException.invalidUsername();
        }
        if (ValidateUtil.invalidPassword(request.getPassword())) {
            log.warn("Signup::(block). Password is null. {}", request);
            throw AuthException.invalidPassword();
        }

        if (accountRepository.existsByUserName(request.getUsername())) {
            log.warn("Signup::(block). Username is already exist. {}", request);
            throw AuthException.usernameAlreadyExist();
        }

        if (accountRepository.existsByName(request.getName())) {
            log.warn("Signup::(block). Name is already exist. {}", request);
            throw AuthException.nameAlreadyExist();
        }
        if (accountRepository.existsByMobile(request.getMobile())) {
            log.warn("Signup::(block). Mobile is already exist. {}", request);
            throw AuthException.mobileAlreadyExist();
        }

        Account entity = new Account();
        entity.setName(request.getName());
        entity.setMobile(request.getMobile());
        entity.setUserName(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        Account res = this.accountRepository.save(entity);

        return ResponseUtil.success(getToken(res));
    }

    public ResponseEntity<?> login(AuthLoginRequest request) throws BaseException {
        if (Objects.isNull(request.getUsername())) {
            log.warn("Login::(block). Username is null. {}", request);
            throw AuthException.invalidUsername();
        }
        if (ValidateUtil.invalidPassword(request.getPassword())) {
            log.warn("Login::(block). Password is null. {}", request);
            throw AuthException.invalidPassword();
        }
        Optional<Account> accountOptional = this.accountRepository.findByUserName(request.getUsername());
        if (!accountOptional.isPresent()) {
            log.warn("Login::(block). Username is not exist. {}", request);
            throw AuthException.accountNotFound();
        }
        Account account = accountOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            log.warn("Login::(block). Password is not match. {}", request);
            throw AuthException.invalidPassword();
        }
        return ResponseUtil.success(getToken(account));
    }

    public ResponseEntity<?> refreshToken(AuthRefreshTokenRequest request) throws AuthException {
        if (Objects.isNull(request.getToken())) {
            log.warn("RefreshToken::(block). RefreshToken is null. {}", request);
            throw AuthException.invalidRefreshToken();
        }
        if (!tokenService.validates(request.getToken())) {
            log.warn("RefreshToken::(block). RefreshToken is invalid. {}", request);
            throw AuthException.tokenUnauthorized();
        }
        String username = this.tokenService.getUsername(request.getToken());
        Optional<Account> accountOpt = this.accountRepository.findByUserName(username);
        if (!accountOpt.isPresent()) {
            log.warn("RefreshToken::(block). Username is not exist. {}", request);
            throw AuthException.accountNotFound();
        }
        Account account = accountOpt.get();

        return ResponseUtil.success(getToken(account));
    }

    public TokenResponse getToken(Account account) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getUserName(), account.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return tokenService.generateToken(
                Authentication.builder()
                        .id(account.getId())
                        .username(account.getUserName())
                        .build()
        );
    }
}
