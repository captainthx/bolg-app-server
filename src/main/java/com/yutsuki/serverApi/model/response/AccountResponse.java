package com.yutsuki.serverApi.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.util.JsonUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountResponse implements Serializable {
    private long id;
    private String username;
    private String name;
    private String mobile;
    private String avatar;

    public static AccountResponse build(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .username(account.getUserName())
                .mobile(account.getMobile())
                .build();
    }
}
