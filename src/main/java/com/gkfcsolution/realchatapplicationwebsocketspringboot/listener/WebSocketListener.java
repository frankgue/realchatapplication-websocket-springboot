package com.gkfcsolution.realchatapplicationwebsocketspringboot.listener;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.ChatMessage;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.enums.MessageType;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Created on 2025 at 08:53
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:53
 */
@Component
@Slf4j
public class WebSocketListener {
    @Autowired
    private UserService userService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebsocketConnectListener(SessionConnectedEvent event){
        log.info("Connected to websocket");
    }

    @EventListener
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getSessionAttributes().get("username").toString();

        userService.setUserOnlineStatus(username, false);
        ChatMessage chatMessage = ChatMessage.builder()
                .type(MessageType.LEAVE)
                .sender(username)
                .build();

        log.info("User Disconnected from Websocket");

        messagingTemplate.convertAndSend("/topic/public", chatMessage );
    }
}
