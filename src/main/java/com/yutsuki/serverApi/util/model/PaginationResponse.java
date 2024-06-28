package com.yutsuki.serverApi.util.model;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PaginationResponse {
    private Integer current;
    private Integer limit;
    private Integer records;
    private Integer pages;
}
