package com.yutsuki.serverApi.jwt.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseToken {
    private String token;
    private Long expire;
}
