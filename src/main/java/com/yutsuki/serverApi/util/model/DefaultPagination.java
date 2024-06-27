package com.yutsuki.serverApi.util.model;

import lombok.Data;

@Data
public class DefaultPagination {
    private int page = 1;
    private int limit = 10;
}
