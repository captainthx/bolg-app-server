package com.yutsuki.serverApi.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdAvatarRequest {
    private String avatarUrl;
}
