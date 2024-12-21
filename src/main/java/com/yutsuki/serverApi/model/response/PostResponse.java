package com.yutsuki.serverApi.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yutsuki.serverApi.common.PostStatus;
import com.yutsuki.serverApi.entity.FavoritePost;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
    private String postImage;
    private List<String> tags;
    private List<AccountResponse> postLikes;
    private AccountResponse author;
    private boolean isFavorite;
    private boolean isLike;

    public static PostResponse build(Post post, boolean isFavorite, boolean isLike) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setCdt(post.getCdt());
        response.setStatus(post.getStatus());
        response.setLikeCount(post.getLikeCount());
        response.setPostImage(post.getPostImage());
        if (!ObjectUtils.isEmpty(post.getAccount())) {
            response.setAuthor(AccountResponse.build(post.getAccount()));
        }
        if (!ObjectUtils.isEmpty(post.getTags())) {
            response.setTags(TagResponse.buildToString(post.getTags()));
        }
        if (!ObjectUtils.isEmpty(post.getPostLikes())) {
            response.setPostLikes(PostLikeResponse.buildToList(post.getPostLikes()));
        }
        response.setFavorite(isFavorite);
        response.setLike(isLike);
        return response;
    }

    public static List<PostResponse> buildToList(List<Post> posts, boolean isFavorite, boolean isLike) {
        return posts.stream().map(post -> PostResponse.build(post, isFavorite, isLike)).collect(Collectors.toList());
    }
}