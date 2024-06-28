package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.CreatePostRequest;
import com.yutsuki.serverApi.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/post")
public class PostController {
    @Resource
    private PostService postService;

    @GetMapping
    public ResponseEntity<?> getPostList(Pagination pagination) {
        return postService.getPostList(pagination);
    }


    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request) throws BaseException {
        return postService.createPost(request);
    }
}
