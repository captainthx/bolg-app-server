package com.yutsuki.serverApi.jwt.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class TokenPayload {
    private String tokenId;
    private Long expire;
    private String signature;
}
