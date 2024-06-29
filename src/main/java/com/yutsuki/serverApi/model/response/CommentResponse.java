package com.yutsuki.serverApi.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yutsuki.serverApi.entity.Comment;
import com.yutsuki.serverApi.util.JsonUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link com.yutsuki.serverApi.entity.Comment}
 */
@Getter
@Setter
@ToString
@Builder
public class CommentResponse implements Serializable {
    private Long id;
    private AccountResponse account;
    private String comment;
    @JsonSerialize(using = JsonUtil.jsonTimeSerializer.class)
    private LocalDateTime cdt;

    public static List<CommentResponse> buildToList(List<Comment> comments) {
        return comments.stream().map(e -> CommentResponse.builder()
                .id(e.getId())
                .account(AccountResponse.build(e.getAccount()))
                .comment(e.getComment())
                .cdt(e.getCdt())
                .build()).collect(Collectors.toList());
    }

    public static CommentResponse build(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .account(AccountResponse.build(comment.getAccount()))
                .comment(comment.getComment())
                .cdt(comment.getCdt())
                .build();
    }
}