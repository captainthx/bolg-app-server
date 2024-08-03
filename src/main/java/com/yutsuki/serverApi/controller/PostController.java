package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.CreatePostRequest;
import com.yutsuki.serverApi.model.request.QueryPostRequest;
import com.yutsuki.serverApi.model.request.UpdPostRequest;
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
    public ResponseEntity<?> getPostList(QueryPostRequest query) {
        return postService.getPostList(query);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) throws BaseException {
        return postService.getPostById(id);
    }


    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request) throws BaseException {
        return postService.createPost(request);
    }

    @PatchMapping("/likes/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId) throws BaseException {
        return postService.likePost(postId);
    }

    @PatchMapping
    public ResponseEntity<?> updatePost(@RequestBody UpdPostRequest request) throws BaseException {
        return postService.updatePost(request);
    }
}
