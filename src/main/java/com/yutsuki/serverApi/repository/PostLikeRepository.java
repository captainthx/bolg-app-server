package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}