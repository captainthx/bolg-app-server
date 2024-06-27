package com.yutsuki.serverApi;

import com.yutsuki.serverApi.common.RedisKey;
import com.yutsuki.serverApi.jwt.Authentication;
import com.yutsuki.serverApi.jwt.model.TokenResponse;
import com.yutsuki.serverApi.jwt.model.ValidateBaseToken;
import com.yutsuki.serverApi.service.HashBaseToken;
import com.yutsuki.serverApi.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
//@Component("devRunner")
public class DevRunner implements ApplicationRunner {
    @Resource
    private HashBaseToken hashBaseToken;
    @Resource
    TokenService tokenService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("dev runner");
        boolean validates = tokenService.validates("eyJraWQiOiJ5dXRzdWtpIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJjYXBwIiwiYXV0aCI6MSwiaXNzIjoieW90c3VraSIsImV4cCI6MTcxODI2NDEwMSwiaWF0IjoxNzE4MjYwNTAxLCJqdGkiOiJiYTQxN2NiZS1iOWU1LTQ3MDYtYTZhMi05YzNiODE3NjVhYjAifQ.CbXKzfSLr1pempifduP-k2pALbWciZ4kqLYaP5r4iRHHQLasDPxVdR7N5lvQNuy0nNtZj7EmFln7Y01ylbiYLIGlrfQ8L_EL5rarc8hVknV_AkCVaSvNOl8BIOaqojAArIMMZKpdJjW7-WkGieO2Q3b80oPdyvbMa1b10cWsHCRMpWPGnTqZVvGL13cTzo-UHgKeN1GXQZyk6suCQ8y54ww_CIxhpS2pkwn5pwNmXE1GRgxmvJkUKDjOw30E3TLhA30cj5kA0c5v9qqTAU7iLSfoPaVqLrIjuwlI6i0b-UenVxsubH08wehy-5GPbturtYQVf0mCfIL7i35uooOsDw");
        log.info("validates: {}", validates);
        //        TokenResponse tokenResponse = this.hashBaseToken.generateToken(Authentication.builder().id(1L).username("cappt").build());
//        log.info("tokenResponse: {}", tokenResponse);
        ValidateBaseToken validateBaseToken = ValidateBaseToken.builder()
                .uid(1L)
                .token("YmYzNWJkMTQtN2ZjNC00MzUxLWJmNWItZTNkZGUxZDcyODE0OjE2OTYzMTg4NDgxOTA6MjNlMDEyMDVkMTQxNmIyMDUxZTg2NDBhZDY5MmU4ZDRiZTNmNTdjY2NlZmVlZDc5YzlkYjQ3N2I0YmU2MzkzNDkxNWVmZWRjMTIxZWZjZTJiYzcyZWUwMTRjNDllOTlhOThkYzBiNWViNWE2NzFjYzgyYmJlY2UxOWFiNjQxMjg=")
                .redisKey(RedisKey.ACCESS_TOKEN.getValue())
                .build();

        this.hashBaseToken.validates(validateBaseToken);
    }
}
