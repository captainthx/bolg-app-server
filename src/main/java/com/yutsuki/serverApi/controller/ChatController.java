package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.common.MessageStatus;
import com.yutsuki.serverApi.jwt.UserDetailsImp;
import com.yutsuki.serverApi.model.Message;
import com.yutsuki.serverApi.websocket.WebSocketEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Objects;

@Controller
@Slf4j
public class ChatController {
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;
    @Resource
    private WebSocketEvent webSocketEvent;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message handlePublicMessage(@Payload Message message){

        if (Objects.equals(message.getStatus(), MessageStatus.JOIN)) {
//            webSocketEvent.addUser(message.getSenderName(), headerAccessor.getSessionId());
        }
        return message;
    }

    @MessageMapping("/private-message")
    public void handlePrivateMessage(@Payload Message message) {
        log.info("PrivateMessage: {}", message);
        // change to send to user id 1/private/message
        this.simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
    }

}