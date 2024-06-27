package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.PostStatus;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.entity.Comment;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.exception.AuthException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.PostException;
import com.yutsuki.serverApi.model.request.CreatePostRequest;
import com.yutsuki.serverApi.model.response.AccountResponse;
import com.yutsuki.serverApi.model.response.PostResponse;
import com.yutsuki.serverApi.repository.CommentRepository;
import com.yutsuki.serverApi.repository.PostLikeRepository;
import com.yutsuki.serverApi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PostService {
    @Resource
    private SecurityService securityService;
    @Resource
    private PostRepository postRepository;
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private PostLikeRepository postLikeRepository;

    public void getPostList() {


    }

    public ResponseEntity<?> createPost(CreatePostRequest request) throws BaseException {
        if (ObjectUtils.isEmpty(request.getTitle())) {
            log.warn("CratePost::(block).invalid post title. {}", request);
            throw PostException.invalidPostTitle();
        }
        if (ObjectUtils.isEmpty(request.getContent())) {
            log.warn("CratePost::(block).invalid post content. {}", request);
            throw PostException.invalidPostContent();
        }
        Optional<PostStatus> postStatus = PostStatus.find(request.getStatus().toUpperCase());
        if (!postStatus.isPresent()) {
            log.warn("CratePost::(block).invalid post status. {}", request);
            throw PostException.invalidPostStatus();
        }
        Account account = securityService.getUserDetail();
        if (Objects.isNull(account)) {
            log.warn("CratePost::(block).invalid account. {}", request);
            throw AuthException.accountNotFound();
        }
        PostStatus status = postStatus.get();
        Post entity = new Post();
        entity.setAccount(account);
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setStatus(status);
        Post response = postRepository.save(entity);

        // create response
        PostResponse responses = build(response, account, response.getComments());
        return ResponseUtil.success(responses);
    }

    public void updatePost() {
    }

    public void deletePost() {
    }

    public void likePost() {
    }

    public void commentPost() {
    }

    //todo: add response comment list
    public static PostResponse build(Post post, Account account, List<Comment> comments) {
        return PostResponse.builder()
                .id(post.getId())
                .cdt(post.getCdt())
                .accountResponse(AccountResponse.build(account))
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .likeCount(post.getLikeCount())
                .build();
    }
}
