package com.yutsuki.serverApi;

import com.yutsuki.serverApi.email.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class BlogAppServerApplicationTests {
    @Autowired
    private SendMailService sendMailService;



}
