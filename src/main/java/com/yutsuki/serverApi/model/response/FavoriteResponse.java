package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.Favorite;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link Favorite}
 */
@Getter
@Setter
@ToString
public class FavoriteResponse implements Serializable {
    private AccountResponse account;


    public static FavoriteResponse build(Favorite favorite) {
        FavoriteResponse response = new FavoriteResponse();
        response.setAccount(AccountResponse.build(favorite.getAccount()));
        return response;
    }

    public static List<FavoriteResponse> buildToList(List<Favorite> favorites) {
        return favorites.stream().map(FavoriteResponse::build).collect(Collectors.toList());
    }

}