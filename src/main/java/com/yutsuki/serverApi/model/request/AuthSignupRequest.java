package com.yutsuki.serverApi.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthSignupRequest {
    @Length(min = 4,max = 10)
    private String name;
    @Length(min = 4,max = 10)
    private String username;
    @Length(min = 4)
    private String password;
    @Length(max = 10)
    private String mobile;
}
