package com.yutsuki.serverApi.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yutsuki.serverApi.common.PostStatus;
import com.yutsuki.serverApi.util.JsonUtil;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.yutsuki.serverApi.entity.Post}
 */
@Getter
@Setter
@ToString
@Builder
public class PostResponse implements Serializable {
    private Long id;
    @JsonSerialize(using = JsonUtil.jsonTimeSerializer.class)
    private LocalDateTime cdt;
    private AccountResponse account;
    private String title;
    private String content;
    private PostStatus status;
    private Integer likeCount;
    private List<CommentResponse> comments;
}