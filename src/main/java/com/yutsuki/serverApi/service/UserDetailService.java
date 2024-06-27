package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.jwt.Authentication;
import com.yutsuki.serverApi.jwt.UserDetailsImp;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public UserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetailsImp loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return UserDetailsImp.build(
                Authentication.builder()
                        .id(user.getId())
                        .username(user.getUserName())
                        .build()
        );
    }
}
