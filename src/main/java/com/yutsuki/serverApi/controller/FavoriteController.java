package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.service.FavoritePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/favorite")
public class FavoriteController {
    @Resource
    private FavoritePostService favoritePostService;

    @GetMapping
    public ResponseEntity<?> getFavoritePostList(Pagination pagination) throws BaseException {
        return favoritePostService.getFavoritePostList(pagination);
    }

    @PostMapping
    public ResponseEntity<?> favoritePost(Long postId) throws BaseException {
        return favoritePostService.favoritePost(postId);
    }
}
