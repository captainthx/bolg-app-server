package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.CreatePostRequest;
import com.yutsuki.serverApi.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/post")
public class PostController {
    @Resource
    private PostService postService;


    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request) throws BaseException {
        return postService.createPost(request);
    }
}
