package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.entity.Comment;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.PostException;
import com.yutsuki.serverApi.model.request.CreateCommentPostRequest;
import com.yutsuki.serverApi.model.response.CommentResponse;
import com.yutsuki.serverApi.repository.CommentRepository;
import com.yutsuki.serverApi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private PostRepository postRepository;
    @Resource
    private SecurityService securityService;

    public ResponseEntity<?> createComment(CreateCommentPostRequest request) throws BaseException {
        Account account = securityService.getUserDetail();
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> {
                    log.warn("CreateComment::(block).post not found. {}", request);
                    return PostException.postNotFound();
                });
        if (commentRepository.existsByAccountAndPost_Id(account, request.getPostId())) {
            log.warn("CreateComment::(block).comment already exists. {}", request);
            throw PostException.commentAlreadyExists();
        }
        if (ObjectUtils.isEmpty(request.getComment())) {
            log.warn("CreateComment::(block).invalid comment. {}", request);
            throw PostException.invalidComment();
        }
        Comment entity = new Comment();
        entity.setAccount(account);
        entity.setPost(post);
        entity.setComment(request.getComment());
        Comment response = commentRepository.save(entity);
        return ResponseUtil.success(CommentResponse.build(response));
    }
}
