package com.yutsuki.serverApi;

import com.yutsuki.serverApi.service.TokenService;
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
    private TokenService tokenService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testValidates() {
        String validToken = "eyJraWQiOiJ5dXRzdWtpIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJjYXBwIiwiYXV0aCI6MSwiaXNzIjoieW90c3VraSIsImV4cCI6MTcxODQyMTY0NSwiaWF0IjoxNzE4NDE4MDQ1LCJqdGkiOiIwMjI5MWY4YS1lOTc5LTRjMTMtODBkMy00ZTdiZTUyZjA0YWIifQ.d5luQOIu5weqS9sb5s73mkA3SG22emd285QbgROfcrrsFmou5xg-5SDlOzR5c87B2OCJcb7--WxOUnibUnNhF3Khg_5DoOoh_OjITgEo77RV9XRYRmHkk8pJ0zCpGyZXihZ9DqjDf3Aym0dPVNw79QlkOYi2zBFIjiXMOWOHymetoJzRxKEihnSHdvW3MFm3uxwMSYb9cY_Of4sF6L3wD43h0SQQzdV7wZLUdsprEknKxc6CGF2RPZzidVPVCuO1niJQOqGDcVC00OoI-wx5K-QBRoxDXzrcps4SGdYRZFSZTvze3jB4RDuUYYPRZbxKPJueg6_z4qmFuSTjCIEUPw";
        assertFalse(tokenService.isExpireToken(validToken));
    }

}
