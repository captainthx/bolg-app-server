package com.yutsuki.serverApi.entity;

import com.yutsuki.serverApi.model.response.PostResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link FavoritePost}
 */
@Getter
@Setter
@ToString
public class FavoritePostResponse implements Serializable {
    private Long id;
    private PostResponse post;

    public static FavoritePostResponse build(FavoritePost favoritePost) {
        FavoritePostResponse response = new FavoritePostResponse();
        response.setId(favoritePost.getId());
        response.setPost(PostResponse.build(favoritePost.getPost()));
        return response;
    }

    public static List<FavoritePostResponse> buildToList(List<FavoritePost> favoritePosts) {
        return favoritePosts.stream().map(FavoritePostResponse::build).collect(Collectors.toList());
    }
}