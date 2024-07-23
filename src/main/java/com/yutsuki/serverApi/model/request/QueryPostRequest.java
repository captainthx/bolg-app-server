package com.yutsuki.serverApi.model.request;

import com.yutsuki.serverApi.common.Pagination;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryPostRequest  extends Pagination {
   private String search;

    public QueryPostRequest(Integer page, Integer size, String sort) {
        super(page, size, sort);
    }
}
