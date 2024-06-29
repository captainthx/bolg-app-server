package com.yutsuki.serverApi.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateCommentPostRequest {
    private Long postId;
    private String comment;
}
