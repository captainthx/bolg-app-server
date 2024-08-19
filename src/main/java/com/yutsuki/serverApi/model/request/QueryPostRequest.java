package com.yutsuki.serverApi.model.request;

import com.yutsuki.serverApi.common.Pagination;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryPostRequest {
    private String search;

}
