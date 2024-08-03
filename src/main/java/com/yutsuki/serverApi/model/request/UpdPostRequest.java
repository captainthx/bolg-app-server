package com.yutsuki.serverApi.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdPostRequest {
    private Long postId;
    private String postImage;
    private String status;
    private String title;
    private String content;
}
