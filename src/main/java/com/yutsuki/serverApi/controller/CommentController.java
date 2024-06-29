package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.model.request.CreateCommentPostRequest;
import com.yutsuki.serverApi.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CreateCommentPostRequest request) throws BaseException {
        return commentService.createComment(request);
    }
}
