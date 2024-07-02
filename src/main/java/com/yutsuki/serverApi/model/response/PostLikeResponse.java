package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.entity.PostLike;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DTO for {@link PostLike}
 */
@Getter
@Setter
@ToString
public class PostLikeResponse implements Serializable {
    private AccountResponse account;

    public static PostLikeResponse build(PostLike postLike) {
        PostLikeResponse response = new PostLikeResponse();
        response.setAccount(AccountResponse.build(postLike.getAccount()));
        return response;
    }

    public static List<PostLikeResponse> buildToList(List<PostLike> postLikes) {
        Map<Account, List<PostLike>> groupedByAccount = postLikes.stream()
                .collect(Collectors.groupingBy(PostLike::getAccount));

        return groupedByAccount.keySet().stream()
                .map(likes -> {
                    PostLikeResponse response = new PostLikeResponse();
                    response.setAccount(AccountResponse.build(likes));
                    return response;
                }).collect(Collectors.toList());
    }
}