package com.yutsuki.serverApi.model.request;

import com.yutsuki.serverApi.common.Pagination;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class QueryPostRequest  extends Pagination {
    private Long id;
    private String title;
    private String content;
    private List<String > tags;

    public QueryPostRequest(Integer page, Integer size, String sort) {
        super(page, size, sort);
    }
}
