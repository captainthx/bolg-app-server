package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}