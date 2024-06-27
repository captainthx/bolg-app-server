package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.jwt.Authentication;
import com.yutsuki.serverApi.jwt.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class HashBaseToken {

    @Value("${jwt.isExpire}")
    private Long isExpire;
    @Resource
    private RedisService redisService;

    public TokenResponse generateToken(Authentication authentication) {
        BaseToken accessToken = this.generateAccessToken(this.getIsExpire(isExpire));
        BaseToken refreshToken = this.generateRefreshToken(this.getIsExpire(isExpire));

        this.setAccessToken(accessToken.getToken(), authentication.getId());
        this.setRefreshToken(refreshToken.getToken(), authentication.getId());

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessExpire(accessToken.getExpire())
                .refreshToken(refreshToken.getToken())
                .refreshExpire(refreshToken.getExpire())
                .build();
    }

    public BaseToken generateAccessToken(long isExpire) {
        return generateToken(isExpire);
    }

    public BaseToken generateRefreshToken(long isExpire) {
        return generateToken(isExpire);
    }

    public BaseToken generateToken(Long expire) {
        String tokenId = UUID.randomUUID().toString();
        String secretKey = this.randomSecretKey();
        String signature = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, secretKey).hmacHex(tokenId + ":" + expire + ":" + secretKey);
        return BaseToken.builder()
                .token(this.encode(tokenId, expire, signature))
                .expire(expire)
                .build();
    }

    private String encode(String tokenId, Long expire, String signature) {
        return Base64.getEncoder().encodeToString((tokenId + ":" + expire + ":" + signature).getBytes(StandardCharsets.UTF_8));
    }

    private String decode(String token) {
        return new String(Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8)));
    }

    public void setRefreshToken(String token, Long uid) {
        TokenPayload payload = this.tokenPayload(token);
        if (Objects.isNull(payload)) {
            log.debug("payload is null {}", payload);
            return;
        }
        this.redisService.set("refreshToken:" + uid, payload.getTokenId());
        log.info("set refresh tokenId to redis {}", payload.getTokenId());
    }

    public void setAccessToken(String token, Long uid) {
        TokenPayload payload = this.tokenPayload(token);
        if (Objects.isNull(payload)) {
            log.debug("payload is null {}", payload);
            return;
        }
        this.redisService.set("accessToken:" + uid, payload.getTokenId());
        log.info("set access tokenId to redis {}", payload.getTokenId());
    }

    public String receiveTokenId(ReceiveTokenIdRequest receive) {
        Object obj = this.redisService.get(receive.getRedisKey() + ":" + receive.getUid()
        );
        return obj.toString();
    }

    public boolean validates(ValidateBaseToken validate) {
        TokenPayload payload = this.tokenPayload(validate.getToken());
        if (this.isExpired(payload.getExpire())) {
            log.warn("Validate::(block). tokenId is expired. {}", payload);
            return false;
        }
        Object tokenId = this.redisService.get(validate.getRedisKey() + ":" + validate.getUid());
        if (!Objects.equals(payload.getTokenId(), tokenId)) {
            log.warn("Validate::(block). tokenId is not valid. {}", tokenId);
            return false;
        }
        log.info("Validate::(pass). tokenId is valid. {}", payload);
        return true;
    }


    public boolean isExpired(long tokenExpire) {
        if (tokenExpire < Instant.now().toEpochMilli()) {
            log.warn("IsExpire::(block). token is expired");
            return true;
        }
        return false;
    }


    public TokenPayload tokenPayload(String token) {
        String decode = this.decode(token);
        String[] split = decode.split(":");
        return TokenPayload.builder()
                .tokenId(split[0])
                .expire(Long.parseLong(split[1]))
                .signature(split[2])
                .build();
    }

    private String randomSecretKey() {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.toString();
    }


    private Long getIsExpire(long expire) {
        return Instant.now().plus(expire, ChronoUnit.HOURS).toEpochMilli();
    }
}
