package com.yutsuki.serverApi.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Authentication  {
    private Long id;
    private String username;
}
