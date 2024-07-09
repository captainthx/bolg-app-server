package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByAccount_Id(Long id);

    boolean existsByAccount_IdAndPost_Id(Long userId, Long postId);
}