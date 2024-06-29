package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.common.PostStatus;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.entity.Comment;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.exception.AuthException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.PostException;
import com.yutsuki.serverApi.model.request.CreateCommentPostRequest;
import com.yutsuki.serverApi.model.request.CreatePostRequest;
import com.yutsuki.serverApi.model.request.QueryPostRequest;
import com.yutsuki.serverApi.model.response.AccountResponse;
import com.yutsuki.serverApi.model.response.CommentResponse;
import com.yutsuki.serverApi.model.response.PostResponse;
import com.yutsuki.serverApi.repository.CommentRepository;
import com.yutsuki.serverApi.repository.PostLikeRepository;
import com.yutsuki.serverApi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
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

    //    @Cacheable(value = "post",key = "#pagination.pageNumber" )
    public ResponseEntity<?> getPostList(Pagination pagination, QueryPostRequest query) {

        Post search = new Post();
        search.setId(query.getId());
        search.setTitle(query.getTitle());
        search.setContent(query.getContent());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Post> example = Example.of(search, matcher);

        Page<Post> posts = postRepository.findAll(example, pagination);
        if (posts.isEmpty()) {
            return ResponseUtil.success();
        }
        List<PostResponse> responses = posts.map(post -> {
            Account account = post.getAccount();
            List<Comment> comments = post.getComments();
            return build(post, Objects.nonNull(account) ? account : null, Objects.nonNull(comments) ? comments : null);
        }).getContent();
        return ResponseUtil.successList(posts, responses);
    }
//    public ResponseEntity<?> getPostListResponse(Pagination pagination) {
//        List<PostResponse> responses = getPostList(pagination);
//        Page<Post> posts = postRepository.findAll(pagination);
//        return ResponseUtil.successList(posts, responses);
//    }

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

    public ResponseEntity<?> updatePost() {
        return ResponseUtil.success();
    }

    public ResponseEntity<?> deletePost() {
        return ResponseUtil.success();
    }

    public ResponseEntity<?> likePost() {
        return ResponseUtil.success();
    }



    public static PostResponse build(Post post, Account account, List<Comment> comments) {
        return PostResponse.builder()
                .id(post.getId())
                .cdt(post.getCdt())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .likeCount(post.getLikeCount())
                .account(AccountResponse.build(account))
                .comments(Objects.nonNull(comments) ? CommentResponse.buildToList(comments) : null)
                .build();
    }
}
