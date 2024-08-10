package com.yutsuki.serverApi.entity;

import com.yutsuki.serverApi.model.response.PostFavoriteResponse;
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
public class FavoriteResponse implements Serializable {
    private Long id;
    private PostFavoriteResponse post;

    public static FavoriteResponse build(FavoritePost favoritePost) {
        FavoriteResponse response = new FavoriteResponse();
        response.setId(favoritePost.getId());
        response.setPost(PostFavoriteResponse.build(favoritePost.getPost()));
        return response;
    }

    public static List<FavoriteResponse> buildToList(List<FavoritePost> favoritePosts) {
        return favoritePosts.stream().map(FavoriteResponse::build).collect(Collectors.toList());
    }
}