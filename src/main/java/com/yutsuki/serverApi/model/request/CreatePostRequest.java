package com.yutsuki.serverApi.model.request;

import com.yutsuki.serverApi.common.PostStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CreatePostRequest {
    private String title;
    private String content;
    private String status;
    private List<String> tags;
}
