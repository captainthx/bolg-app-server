package com.yutsuki.serverApi;

import com.yutsuki.serverApi.email.SendMailService;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.service.TokenService;
import com.yutsuki.serverApi.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class SpringBootUtimateApplicationTests {
    @Autowired
    private SendMailService sendMailService;



}
