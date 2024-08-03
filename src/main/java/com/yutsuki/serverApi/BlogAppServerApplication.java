package com.yutsuki.serverApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching
public class BlogAppServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAppServerApplication.class, args);
    }

    @PostConstruct
    public void init(){
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }


}
