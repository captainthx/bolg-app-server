package com.yutsuki.serverApi.jwt.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long accessExpire;
    private Long refreshExpire;

}
