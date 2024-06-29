package com.yutsuki.serverApi.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdAccountRequest {
    private String mobile;
    private String name;
    private String avatarName;
}
