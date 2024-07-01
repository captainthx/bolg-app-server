package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.TagsPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TagsPostRepository extends JpaRepository<TagsPost, Long> {


}