package com.yutsuki.serverApi.model.request;

import com.yutsuki.serverApi.common.PostStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreatePostRequest {
    private String title;
    private String content;
    private String status;
}
