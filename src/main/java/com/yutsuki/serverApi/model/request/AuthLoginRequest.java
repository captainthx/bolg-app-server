package com.yutsuki.serverApi.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
