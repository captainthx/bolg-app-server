package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.AccountException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.response.AccountResponse;
import com.yutsuki.serverApi.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Slf4j
public class AccountService {
    @Resource
    private AccountRepository accountRepository;
    @Resource
    private SecurityService securityService;


    public ResponseEntity<?> findAll(Pagination pagination) throws AccountException {
        Page<Account> response = this.accountRepository.findAll(pagination);
        if (response.isEmpty()) {
            throw AccountException.accountListEmpty();
        }
        return ResponseUtil.successList(response.map(this::build));
    }

    public ResponseEntity<?> findById() throws BaseException {
        Account userDetail = securityService.getUserDetail();
        Optional<Account> account = this.accountRepository.findById(userDetail.getId());
        if (!account.isPresent()) {
            throw AccountException.accountNotFound();
        }
        return ResponseUtil.success(build(account.get()));
    }

    private AccountResponse build(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .username(account.getUserName())
                .mobile(account.getMobile())
                .build();
    }
}
