package com.yutsuki.serverApi.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yutsuki.serverApi.entity.Comment;
import com.yutsuki.serverApi.utils.JsonUtil;
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

public class CommentResponse implements Serializable {
    private Long id;
    private AccountResponse account;
    private String comment;
    @JsonSerialize(using = JsonUtil.jsonTimeSerializer.class)
    private LocalDateTime cdt;

    public static List<CommentResponse> buildToList(List<Comment> comments) {
        return comments.stream().map(CommentResponse::build).collect(Collectors.toList());
    }

    public static CommentResponse build(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setComment(comment.getComment());
       response.setAccount(AccountResponse.build(comment.getAccount()));
        response.setCdt(comment.getCdt());
        return response;
    }
}