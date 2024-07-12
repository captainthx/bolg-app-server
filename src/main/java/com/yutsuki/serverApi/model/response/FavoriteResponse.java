package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.FavoritePost;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link FavoritePost}
 */
@Getter
@Setter
@ToString
public class FavoriteResponse implements Serializable {
    private AccountResponse account;

    public static FavoriteResponse build(FavoritePost favorite) {
        FavoriteResponse response = new FavoriteResponse();
        response.setAccount(AccountResponse.build(favorite.getAccount()));
        return response;
    }

    public static List<AccountResponse> buildToList(List<FavoritePost> favorites) {
        return favorites.stream().map(e->AccountResponse.build(e.getAccount())).collect(Collectors.toList());
    }

}