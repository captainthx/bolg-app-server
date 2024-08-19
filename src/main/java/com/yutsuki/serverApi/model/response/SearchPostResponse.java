package com.yutsuki.serverApi.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yutsuki.serverApi.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class SearchPostResponse implements Serializable {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cdt;
    private String title;

    public static SearchPostResponse build(Post post) {
        SearchPostResponse response = new SearchPostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setCdt(post.getCdt());
        return response;
    }

    public static List<SearchPostResponse> buildToList(List<Post> posts) {
        return posts.stream().map(SearchPostResponse::build).collect(Collectors.toList());
    }
}