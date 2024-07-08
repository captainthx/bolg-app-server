package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.FavoritePostRequest;
import com.yutsuki.serverApi.service.FavoritePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<?> favoritePost(@RequestBody FavoritePostRequest request) throws BaseException {
        return favoritePostService.favoritePost(request);
    }
}
