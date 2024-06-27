package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserName(String  username);
    Optional<Account> findById(Long id);

    Page<Account>findAll(Pageable pageable);
}
