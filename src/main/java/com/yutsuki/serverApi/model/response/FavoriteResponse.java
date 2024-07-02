package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.FavoritePost;
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
    private PostResponse post;


    public static FavoriteResponse build(FavoritePost favorite) {
        FavoriteResponse response = new FavoriteResponse();
        response.setPost(PostResponse.build(favorite.getPost()));
        return response;
    }

    public static List<FavoriteResponse> buildToList(List<FavoritePost> favorites) {
        return favorites.stream().map(FavoriteResponse::build).collect(Collectors.toList());
    }

}