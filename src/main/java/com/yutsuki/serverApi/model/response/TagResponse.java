package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.TagsPost;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link com.yutsuki.serverApi.entity.TagsPost}
 */
@Getter
@Setter
@ToString
public class TagResponse implements Serializable {
    private String name;

    public static TagResponse build(TagsPost tag) {
        TagResponse response = new TagResponse();
        response.setName(tag.getName());
        return response;
    }

    public static List<TagResponse> buildToList(List<TagsPost> tags) {
        return tags.stream().map(TagResponse::build).collect(Collectors.toList());
    }

    public static List<String> buildToString(List<TagsPost> tags) {
        return tags.stream().map(TagsPost::getName).collect(Collectors.toList());
    }
}