package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.Pagination;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.entity.FavoritePost;
import com.yutsuki.serverApi.entity.FavoritePostResponse;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.PostException;
import com.yutsuki.serverApi.model.request.FavoritePostRequest;
import com.yutsuki.serverApi.repository.FavoritePostRepository;
import com.yutsuki.serverApi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FavoritePostService {
    @Resource
    private FavoritePostRepository favoritePostRepository;
    @Resource
    private PostRepository postRepository;
    @Resource
    private SecurityService securityService;


    public ResponseEntity<?> getFavoritePostList(Pagination pagination) throws BaseException {
        Account account = securityService.getUserDetail();
        Page<FavoritePost> favoritePosts = favoritePostRepository.findByAccount_Id(account.getId(), pagination);
        if (favoritePosts.isEmpty()) {
            return ResponseUtil.successEmpty();
        }
        List<FavoritePostResponse> responses = FavoritePostResponse.buildToList(favoritePosts.getContent());

        return ResponseUtil.successList(favoritePosts, responses);
    }


    public ResponseEntity<?> favoritePost(FavoritePostRequest request) throws BaseException {
        if (ObjectUtils.isEmpty(request.getPostId())) {
            log.warn("FavoritePost::(invalid post id). request: {}", request.getPostId());
            throw PostException.invalidPostId();
        }
        Optional<Post> postOptional = postRepository.findById(request.getPostId());
        if (!postOptional.isPresent()) {
            throw PostException.postNotFound();
        }
        Post post = postOptional.get();
        Account account = securityService.getUserDetail();
        FavoritePost entity = new FavoritePost();
        entity.setPost(post);
        entity.setAccount(account);
        favoritePostRepository.save(entity);
        return ResponseUtil.success();
    }

    public ResponseEntity<?> updateFavoritePost() {
        return ResponseUtil.success();
    }
}
