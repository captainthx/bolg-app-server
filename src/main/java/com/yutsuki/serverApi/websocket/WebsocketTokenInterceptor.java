package com.yutsuki.serverApi.websocket;

import com.yutsuki.serverApi.jwt.UserDetailsImp;
import com.yutsuki.serverApi.service.TokenService;
import com.yutsuki.serverApi.service.UserDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Component
public class WebsocketTokenInterceptor implements ChannelInterceptor {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailService userDetailService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())){
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                throw new IllegalArgumentException("No valid token found");
            }
            String authToken = token.substring(7);
            if (!tokenService.validates(authToken) || tokenService.isExpireToken(authToken)) {
                log.error("Token is invalid or expired");
                throw new IllegalArgumentException("Invalid token or expired");
            }
            String username = tokenService.getUsername(authToken);
            UserDetailsImp userDetails = userDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            accessor.setUser(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Objects.requireNonNull(accessor.getSessionAttributes()).put("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        }
        return message;
    }
}

