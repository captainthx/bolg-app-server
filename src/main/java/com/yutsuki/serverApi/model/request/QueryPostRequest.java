package com.yutsuki.serverApi.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryPostRequest {
    private Long id;
    private String title;
    private String content;
}
