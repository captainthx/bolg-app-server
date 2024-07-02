package com.yutsuki.serverApi.model.response;

import com.yutsuki.serverApi.entity.Account;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class AccountResponse implements Serializable {
    private long id;
    private String username;
    private String name;
    private String mobile;
    private String avatar;

    public static AccountResponse build(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setUsername(account.getUserName());
        response.setName(account.getName());
        response.setMobile(account.getMobile());
        response.setAvatar(account.getAvatar());
        return response;
    }

    public static List<AccountResponse> buildToList(List<Account> accounts) {
        return accounts.stream().map(AccountResponse::build).collect(Collectors.toList());
    }
}
