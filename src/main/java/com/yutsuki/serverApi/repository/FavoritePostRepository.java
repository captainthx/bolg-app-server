package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.FavoritePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FavoritePostRepository extends JpaRepository<FavoritePost, Long> {


    Page<FavoritePost> findByAccount_Id(Long id, Pageable pageable);

    boolean existsByAccount_IdAndPost_Id(Long userId, Long postId);
}