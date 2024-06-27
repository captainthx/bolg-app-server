package com.yutsuki.serverApi.jwt.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Token {
    private String token;
    private Instant expire;
}
