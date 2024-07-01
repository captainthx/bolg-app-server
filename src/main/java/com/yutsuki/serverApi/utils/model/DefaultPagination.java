package com.yutsuki.serverApi.utils.model;

import lombok.Data;

@Data
public class DefaultPagination {
    private int page = 1;
    private int limit = 10;
}
