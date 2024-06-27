package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.Comment;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.yutsuki.serverApi.entity.Comment}
 */
@Value
@Builder
public class CommentResponse implements Serializable {
    Long id;
    AccountResponse account;
    PostResponse post;
    String comment;



}