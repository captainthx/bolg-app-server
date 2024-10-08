package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.common.PostStatus;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.entity.PostLike;
import com.yutsuki.serverApi.entity.TagsPost;
import com.yutsuki.serverApi.exception.AuthException;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.PostException;
import com.yutsuki.serverApi.model.request.CreatePostRequest;
import com.yutsuki.serverApi.model.request.QueryPostRequest;
import com.yutsuki.serverApi.model.request.UpdPostRequest;
import com.yutsuki.serverApi.model.response.PostResponse;
import com.yutsuki.serverApi.model.response.SearchPostResponse;
import com.yutsuki.serverApi.repository.PostLikeRepository;
import com.yutsuki.serverApi.repository.PostRepository;
import com.yutsuki.serverApi.repository.TagsPostRepository;
import com.yutsuki.serverApi.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
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
    private PostLikeRepository postLikeRepository;
    @Resource
    private TagsPostRepository tagsPostRepository;


    public ResponseEntity<?> getPostList(Pagination pagination) {
        Page<Post> posts = postRepository.findAll(pagination);
        if (posts.isEmpty()) {
            return ResponseUtil.successEmpty();
        }
        List<PostResponse> responses = PostResponse.buildToList(posts.getContent());

        return ResponseUtil.successList(posts, responses);
    }

    public ResponseEntity<?> searchPost(QueryPostRequest query) {
        Post search = new Post();
        search.setTitle(query.getSearch());
        search.setContent(query.getSearch());

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Post> example = Example.of(search, matcher);
        List<Post> posts = postRepository.findAll(example, Sort.by(Sort.Direction.DESC, "cdt"));
        if (posts.isEmpty()) {
            return ResponseUtil.successEmpty();
        }
        List<SearchPostResponse> responses = SearchPostResponse.buildToList(posts);

        return ResponseUtil.successList(responses);
    }

    public ResponseEntity<?> getPostById(Long id) throws BaseException {
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            log.warn("GetPostById::(block).post not found. {}", id);
            throw PostException.postNotFound();
        }
        Post post = postOptional.get();
        PostResponse response = PostResponse.build(post);
        return ResponseUtil.success(response);
    }

    @Transactional
    public ResponseEntity<?> createPost(CreatePostRequest request) throws BaseException {
        if (ObjectUtils.isEmpty(request.getPostImage())) {
            log.warn("CratePost::(block).invalid post image. {}", request);
            throw PostException.invalidPostImage();
        }
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
        entity.setPostImage(request.getPostImage());
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setStatus(status);
        Post response = postRepository.save(entity);

        request.getTags().forEach(tag -> {
            TagsPost tagsPost = new TagsPost();
            tagsPost.setPost(response);
            tagsPost.setName(tag);
            tagsPostRepository.save(tagsPost);
        });
        return ResponseUtil.success();
    }

    @Transactional
    public ResponseEntity<?> likePost(Long postId) throws BaseException {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            log.warn("LikePost::(block).post not found. {}", postId);
            return PostException.postNotFound();
        });
        Account account = securityService.getUserDetail();
        if (postLikeRepository.existsByAccount_IdAndPost_Id(account.getId(), postId)) {
            log.warn("LikePost::(block).post already liked. {}", postId);
            throw PostException.postLiked();
        }
        PostLike entity = new PostLike();
        entity.setPost(post);
        entity.setAccount(account);
        postLikeRepository.save(entity);
        // ใช้ dirty checking
        post.setLikeCount(post.getLikeCount() + 1);
        // ไม่จำเป็นต้องเรียก postRepository.save(post) อีก
        return ResponseUtil.success();
    }


    public ResponseEntity<?> updatePost(UpdPostRequest request) throws BaseException {

        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> {
            log.warn("UpdatePost::(block).post not found. {}", request.getPostId());
            return PostException.postNotFound();
        });
        if (!ObjectUtils.isEmpty(request.getPostImage())) {
            if (ValidateUtil.invalidImageLimit(request.getPostImage())) {
                log.warn("UpdatePost::(block).invalid post image. {}", request);
                throw PostException.invalidPostImage();
            }
            post.setPostImage(request.getPostImage());
        }

        if (!ObjectUtils.isEmpty(request.getStatus())) {
            Optional<PostStatus> postStatusOptional = PostStatus.find(request.getStatus().toUpperCase());
            if (!postStatusOptional.isPresent()) {
                log.warn("UpdatePost::(block).invalid post status. {}", request);
                throw PostException.invalidPostStatus();
            }
            PostStatus postStatus = postStatusOptional.get();
            post.setStatus(postStatus);
        }

        if (!ObjectUtils.isEmpty(request.getTitle())) {
            if (ValidateUtil.invalidPostTitleLimit(request.getTitle())) {
                log.warn("UpdatePost::(block).invalid post title. {}", request);
                throw PostException.invalidPostTitle();
            }
            post.setTitle(request.getTitle());
        }

        if (!ObjectUtils.isEmpty(request.getContent())) {
            if (ValidateUtil.invalidPostContentLimit(request.getContent())) {
                log.warn("UpdatePost::(block).invalid post content. {}", request);
                throw PostException.invalidPostContent();
            }
            post.setContent(request.getContent());
        }

        postRepository.save(post);
        return ResponseUtil.success();
    }
}
