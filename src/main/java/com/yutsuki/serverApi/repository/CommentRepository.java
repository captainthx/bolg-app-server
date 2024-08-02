package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByAccount(Account account);

    boolean existsByAccountAndPost_Id(Account account, Long id);

    List<Comment> findByPost_Id(Long id);
}