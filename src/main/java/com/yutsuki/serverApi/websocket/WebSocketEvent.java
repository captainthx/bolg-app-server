package com.yutsuki.serverApi.websocket;

import com.yutsuki.serverApi.jwt.UserDetailsImp;
import com.yutsuki.serverApi.model.response.AccountResponse;
import com.yutsuki.serverApi.service.OnlineOfflineService;
import com.yutsuki.serverApi.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
public class WebSocketEvent {
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;
    @Resource
    TokenService tokenService;
    private final ConcurrentMap<Long, String> userList = new ConcurrentHashMap<>();
    private final Map<String, String> simpSessionIdToSubscriptionId = new ConcurrentHashMap<>();
    @Resource
    private OnlineOfflineService onlineOfflineService;

    @EventListener
    public void handleWebSocketConnect(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        SecurityContext securityContext = (SecurityContext) headerAccessor.getSessionAttributes().get("SPRING_SECURITY_CONTEXT");
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
            log.info("WebSocket connected event:{}", event);
            if (onlineOfflineService.isUserOnline(userDetailsImp.getId())) {
                log.info("User is already online");
            } else {
                onlineOfflineService.addOnlineUser(userDetailsImp);
            }
        }
    }

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        SecurityContext securityContext = (SecurityContext) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("SPRING_SECURITY_CONTEXT");
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
                String subscribeDestination = Objects.requireNonNull(event.getMessage().getHeaders().get("simpDestination")).toString();
                String sessionId = Objects.requireNonNull(event.getMessage().getHeaders().get("simpSessionId")).toString();
                simpSessionIdToSubscriptionId.put(sessionId, subscribeDestination);
                onlineOfflineService.addUserSubscription(userDetailsImp, subscribeDestination);
                broadcastUserList();
            }
        }
    }

    @EventListener
    public void handleUnSubscribeEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        SecurityContext securityContext = (SecurityContext) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("SPRING_SECURITY_CONTEXT");
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
                String sessionId = Objects.requireNonNull(event.getMessage().getHeaders().get("simpSessionId")).toString();
                String unSubscribedChannel = simpSessionIdToSubscriptionId.get(sessionId);
                onlineOfflineService.removeSubscription(userDetailsImp, unSubscribedChannel);
                broadcastUserList();
            }
        }
    }

    private void broadcastUserList() {
        List<AccountResponse> listUserOnline = onlineOfflineService.getListUserOnline();
        simpMessagingTemplate.convertAndSend("/chatroom/users", listUserOnline);
    }

}
