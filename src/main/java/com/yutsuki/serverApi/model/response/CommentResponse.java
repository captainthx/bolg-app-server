package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.Comment;
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
    private LocalDateTime cdt;

    public static List<CommentResponse> build(List<Comment> comments) {
        List<CommentResponse> responses = comments.stream().map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .account(AccountResponse.build(comment.getAccount()))
                .comment(comment.getComment())
                .cdt(comment.getCdt())
                .build()).collect(Collectors.toList());
        return responses;
    }
}