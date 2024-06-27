package com.yutsuki.serverApi.common;

import java.util.Optional;

public enum PostStatus {
    PUBLISH,
    PRIVATE,
    DRAFT,
    DELETED,
    ;

    public static Optional<PostStatus> find(String code) {
        for (PostStatus valuse : PostStatus.values()) {
            if (valuse.name().equals(code))
                return Optional.of(valuse);
        }
        return Optional.empty();
    }
}
