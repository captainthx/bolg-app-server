package com.yutsuki.serverApi.service;

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
import com.yutsuki.serverApi.model.response.PostResponse;
import com.yutsuki.serverApi.repository.PostLikeRepository;
import com.yutsuki.serverApi.repository.PostRepository;
import com.yutsuki.serverApi.repository.TagsPostRepository;
import com.yutsuki.serverApi.utils.PostSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private PostLikeRepository postLikeRepository;
    @Resource
    private TagsPostRepository tagsPostRepository;


    public ResponseEntity<?> getPostList(QueryPostRequest query) {
        Specification<Post> spec = Specification.where(PostSpecifications.hasId(query.getId()))
                .and(PostSpecifications.hasTitle(query.getTitle()))
                .and(PostSpecifications.hasContent(query.getContent()))
                .and(PostSpecifications.hasTags(query.getTags()));
        Page<Post> posts = postRepository.findAll(spec, query);
        if (posts.isEmpty()) {
            return ResponseUtil.successEmpty();
        }
        List<PostResponse> responses = PostResponse.buildToList(posts.getContent());

        return ResponseUtil.successList(posts, responses);
    }

    public ResponseEntity<?> getPostById(Long id) throws BaseException {
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            log.warn("GetPostById::(block).post not found. {}", id);
            throw PostException.postNotFound();
        }
        Post post = postOptional.get();
        log.info("post {}", post.getFavoritePosts());
        PostResponse response = PostResponse.build(post);
        return ResponseUtil.success(response);
    }
    @Transactional
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

        request.getTags().forEach(tag -> {
            TagsPost tagsPost = new TagsPost();
            tagsPost.setPost(response);
            tagsPost.setName(tag);
            tagsPostRepository.save(tagsPost);
        });
        // create response
        PostResponse responses = PostResponse.build(response);
        return ResponseUtil.success(responses);
    }

    @Transactional
    public ResponseEntity<?> likePost(Long postId) throws BaseException {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            log.warn("LikePost::(block).post not found. {}", postId);
            throw PostException.postNotFound();
        }
        Account account = securityService.getUserDetail();
        if (postLikeRepository.existsByAccount_IdAndPost_Id(account.getId(), postId)) {
            log.warn("LikePost::(block).post already liked. {}", postId);
            throw PostException.postLiked();
        }
        Post post = postOptional.get();
        PostLike entity = new PostLike();
        entity.setPost(post);
        entity.setAccount(account);
        postLikeRepository.save(entity);
        // update like count
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
        return ResponseUtil.success();
    }

}
