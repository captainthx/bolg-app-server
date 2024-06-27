package com.yutsuki.serverApi.jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ReceiveTokenIdRequest {
    private String redisKey;
    private long uid;
}
