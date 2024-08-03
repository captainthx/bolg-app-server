package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.AccountException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.UpdAccountRequest;
import com.yutsuki.serverApi.model.response.AccountResponse;
import com.yutsuki.serverApi.repository.AccountRepository;
import com.yutsuki.serverApi.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        return ResponseUtil.successList(response.map(AccountResponse::build));
    }

    public ResponseEntity<?> findById() throws BaseException {
        Account userDetail = securityService.getUserDetail();
        Optional<Account> accountOptional = this.accountRepository.findById(userDetail.getId());
        if (!accountOptional.isPresent()) {
            throw AccountException.accountNotFound();
        }
        Account account = accountOptional.get();
        return ResponseUtil.success(AccountResponse.build(account));
    }

    public ResponseEntity<?> updateAccount(UpdAccountRequest request) throws BaseException {
        Account userDetail = securityService.getUserDetail();
        if (!ObjectUtils.isEmpty(request.getAvatar())) {
            if (ValidateUtil.invalidImageLimit(request.getAvatar())) {
                log.warn("UpdateAccount::(block).invalid avatar. {}", request.getAvatar());
                throw AccountException.invalidAvatar();
            }
            userDetail.setAvatar(request.getAvatar());
        }

        if (!ObjectUtils.isEmpty(request.getName())) {
            if (ValidateUtil.invalidName(request.getName())) {
                log.warn("UpdateAccount::(block).invalid name. {}", request.getName());
                throw AccountException.invalidName();
            }
            userDetail.setName(request.getName());
        }

        if (!ObjectUtils.isEmpty(request.getMobile())) {
            if (ValidateUtil.invalidMobile(request.getMobile())) {
                log.warn("UpdateAccount::(block).invalid mobile. {}", request.getMobile());
                throw AccountException.invalidMobile();
            }
            userDetail.setMobile(request.getMobile());
        }
        Account response = accountRepository.save(userDetail);
        return ResponseUtil.success(AccountResponse.build(response));
    }


}
