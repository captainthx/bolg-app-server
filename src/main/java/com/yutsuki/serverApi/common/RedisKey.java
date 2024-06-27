package com.yutsuki.serverApi.common;

import java.util.Optional;

public enum RedisKey {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");


    private final String value;

    RedisKey(final String key) {
        this.value = key;
    }

    public String getValue() {
        return value;
    }


    public static Optional<RedisKey> find(String key) {
        for (RedisKey valuse : RedisKey.values()) {
            if (valuse.value.equals(key))
                return Optional.of(valuse);
        }
        return Optional.empty();
    }
}
