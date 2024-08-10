package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;

/**
 * DTO for {@link com.yutsuki.serverApi.entity.Post}
 */
@Getter
@Setter
@ToString
public class PostFavoriteResponse implements Serializable {
    private Long id;
    private String title;
    private String content;

    public static PostFavoriteResponse build(Post post){
        PostFavoriteResponse response = new PostFavoriteResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        return response;
    }
}