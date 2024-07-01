package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}