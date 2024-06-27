package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.common.PostStatus;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.yutsuki.serverApi.entity.Post}
 */
@Value
@Builder
public class PostResponse implements Serializable {
    Long id;
    LocalDateTime cdt;
    AccountResponse accountResponse;
    String title;
    String content;
    PostStatus status;
    Integer likeCount;
    List<CommentResponse> comments;

}