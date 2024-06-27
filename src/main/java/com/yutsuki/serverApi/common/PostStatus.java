package com.yutsuki.serverApi.common;

import java.util.Optional;

public enum PostStatus {
    PUBLISH,
    PRIVATE,
    DELETED,
    ;

    public static Optional<PostStatus> find(String code) {
        for (PostStatus value : PostStatus.values()) {
            if (value.name().equals(code))
                return Optional.of(value);
        }
        return Optional.empty();
    }
}
