package com.yutsuki.serverApi.util.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<E> implements Serializable {
    private Integer code;
    private String status;
    private String message;
    private Map<?,?> errors;
    private E result;
    private PaginationResponse pagination;
}
