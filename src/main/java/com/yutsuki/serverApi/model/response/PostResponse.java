package com.yutsuki.serverApi.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yutsuki.serverApi.common.PostStatus;
import com.yutsuki.serverApi.entity.FavoritePost;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.entity.PostLike;
import com.yutsuki.serverApi.entity.TagsPost;
import com.yutsuki.serverApi.utils.Comm;
import com.yutsuki.serverApi.utils.JsonUtil;
import jdk.nashorn.internal.ir.IfNode;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link com.yutsuki.serverApi.entity.Post}
 */
@Getter
@Setter
@ToString
public class PostResponse implements Serializable {
    private Long id;
    @JsonSerialize(using = JsonUtil.jsonTimeSerializer.class)
    private LocalDateTime cdt;
    private String title;
    private String content;
    private PostStatus status;
    private Integer likeCount;
    private List<CommentResponse> comments;
    private List<String> tags;
    private List<AccountResponse> postLikes;
    private AccountResponse author;
    private List<AccountResponse>favoritesPosts;

    public static PostResponse build(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setCdt(post.getCdt());
        response.setStatus(post.getStatus());
        response.setLikeCount(post.getLikeCount());
        response.setAuthor(AccountResponse.build(post.getAccount()));
        if (!ObjectUtils.isEmpty(post.getComments())) {
            response.setComments(CommentResponse.buildToList(post.getComments()));
        }
        if (!ObjectUtils.isEmpty(post.getTags())) {
            response.setTags(TagResponse.buildToString(post.getTags()));
        }
        if (!ObjectUtils.isEmpty(post.getPostLikes())) {
            response.setPostLikes(PostLikeResponse.buildToList(post.getPostLikes()));
        }
        if(!ObjectUtils.isEmpty(post.getFavoritePosts())){
            response.setFavoritesPosts(FavoriteResponse.buildToList(post.getFavoritePosts()));
        }

        return response;
    }

    public static List<PostResponse> buildToList(List<Post> posts) {
        return posts.stream().map(PostResponse::build).collect(Collectors.toList());

    }
}