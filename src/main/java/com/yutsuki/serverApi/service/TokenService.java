package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.jwt.model.Token;
import com.yutsuki.serverApi.jwt.model.TokenResponse;
import com.yutsuki.serverApi.jwt.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class TokenService {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expirationMs}")
    private String expirationMs;

    public TokenService(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public TokenResponse generateToken(Authentication authentication) {
        Token accessToken = generateAccessToken(authentication);
        Token refreshToken = generateRefreshToken(authentication);
        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessExpire(accessToken.getExpire().getEpochSecond())
                .refreshToken(refreshToken.getToken())
                .refreshExpire(refreshToken.getExpire().getEpochSecond())
                .build();
    }


    public Token generateAccessToken(Authentication authentication) {
        return generateToken(authentication, this.setAccessExpire(expirationMs));
    }

    public Token generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, this.setRefreshExpire(expirationMs));
    }

    public Token generateToken(Authentication authentication, Instant expire) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .subject(authentication.getUsername())
                .claim("auth", authentication.getId())
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(expire)
                .build();
        return Token.builder()
                .token(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
                .expire(expire)
                .build();
    }


    public boolean validates(String token) {
        try {
            decoder.decode(token);
            return true;
        } catch (Exception e) {
            log.error("TokenService-[validate](invalid token). error: {}", e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        Jwt decode = decoder.decode(token);
        return decode.getSubject();
    }

    public Long getUserId(String token){
        if (!StringUtils.hasText(token)){
            log.warn("TokenService-[getUserId](token is empty) {}",token);
        }
        Jwt decode = decoder.decode(token);
        return decode.getClaim("auth");
    }


    private Instant setRefreshExpire(String expirationMs) {
        return Instant.now().plusMillis(DurationStyle.detectAndParse(expirationMs).toMillis()).plus(1, ChronoUnit.HOURS);
    }

    private Instant setAccessExpire(String expirationMs) {
        return Instant.now().plusMillis(DurationStyle.detectAndParse(expirationMs).toMillis());
    }

    public boolean isExpireToken(String token) {
        try {
            Jwt decode = decoder.decode(token);
            return Instant.now().isAfter(Objects.requireNonNull(decode.getExpiresAt()));
        } catch (Exception e) {
            log.debug("TokenService-[isExpireToken](invalid token). error: {}", e.getMessage());
            return false;
        }
    }
}
