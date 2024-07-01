package com.yutsuki.serverApi;

import com.github.javafaker.Faker;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.repository.AccountRepository;
import com.yutsuki.serverApi.repository.PostRepository;
import com.yutsuki.serverApi.service.HashBaseToken;
import com.yutsuki.serverApi.service.SecurityService;
import com.yutsuki.serverApi.service.TokenService;
import com.yutsuki.serverApi.utils.Comm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
//@Component("devRunner")
@AllArgsConstructor
public class DevRunner implements ApplicationRunner {

    private final HashBaseToken hashBaseToken;
    private final TokenService tokenService;
    private final PostRepository postRepository;
    private final SecurityService securityService;
    private final AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.info("dev runner");
//        boolean validates = tokenService.validates("eyJraWQiOiJ5dXRzdWtpIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJjYXBwIiwiYXV0aCI6MSwiaXNzIjoieW90c3VraSIsImV4cCI6MTcxODI2NDEwMSwiaWF0IjoxNzE4MjYwNTAxLCJqdGkiOiJiYTQxN2NiZS1iOWU1LTQ3MDYtYTZhMi05YzNiODE3NjVhYjAifQ.CbXKzfSLr1pempifduP-k2pALbWciZ4kqLYaP5r4iRHHQLasDPxVdR7N5lvQNuy0nNtZj7EmFln7Y01ylbiYLIGlrfQ8L_EL5rarc8hVknV_AkCVaSvNOl8BIOaqojAArIMMZKpdJjW7-WkGieO2Q3b80oPdyvbMa1b10cWsHCRMpWPGnTqZVvGL13cTzo-UHgKeN1GXQZyk6suCQ8y54ww_CIxhpS2pkwn5pwNmXE1GRgxmvJkUKDjOw30E3TLhA30cj5kA0c5v9qqTAU7iLSfoPaVqLrIjuwlI6i0b-UenVxsubH08wehy-5GPbturtYQVf0mCfIL7i35uooOsDw");
//        log.info("validates: {}", validates);
//        //        TokenResponse tokenResponse = this.hashBaseToken.generateToken(Authentication.builder().id(1L).username("cappt").build());
////        log.info("tokenResponse: {}", tokenResponse);
//        ValidateBaseToken validateBaseToken = ValidateBaseToken.builder()
//                .uid(1L)
//                .token("YmYzNWJkMTQtN2ZjNC00MzUxLWJmNWItZTNkZGUxZDcyODE0OjE2OTYzMTg4NDgxOTA6MjNlMDEyMDVkMTQxNmIyMDUxZTg2NDBhZDY5MmU4ZDRiZTNmNTdjY2NlZmVlZDc5YzlkYjQ3N2I0YmU2MzkzNDkxNWVmZWRjMTIxZWZjZTJiYzcyZWUwMTRjNDllOTlhOThkYzBiNWViNWE2NzFjYzgyYmJlY2UxOWFiNjQxMjg=")
//                .redisKey(RedisKey.ACCESS_TOKEN.getValue())
//                .build();
//
//        this.hashBaseToken.validates(validateBaseToken);
        Faker faker = new Faker();
//        Account account = securityService.getUserDetail();

        Set<Post> posts = new HashSet<>();
        Set<Account> accounts = new HashSet<>();
        for (int i = 0; i < 20; i++) {
//            Account account = new Account();
//            account.setName(faker.name().name());
//            account.setUserName(faker.name().username());
//            account.setMobile(faker.phoneNumber().phoneNumber());
//            account.setPassword("123456");
//            accounts.add(account);
//            Post post = new Post();
//            post.setTitle(faker.book().title());
//            post.setContent(faker.lorem().paragraph());
//            post.setStatus(PostStatus.PUBLISH);
//            post.setAccount(account);
//            posts.add(post);
        }
//        accountRepository.saveAll(accounts);
//        postRepository.saveAll(posts);
    }
}
