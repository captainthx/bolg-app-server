package com.yutsuki.serverApi.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthRefreshTokenRequest {
    @NotNull
    private String token;
}
