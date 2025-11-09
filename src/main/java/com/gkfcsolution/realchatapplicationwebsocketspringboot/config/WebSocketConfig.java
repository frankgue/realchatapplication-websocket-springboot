package com.gkfcsolution.realchatapplicationwebsocketspringboot.config;

/**
 * Created on 2025 at 08:52
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:52
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry endpointRegistry) {
        endpointRegistry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173","http://localhost:3000")
                .withSockJS();
    }

    // Enable simple broker for group and user-specifi/private chat
    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        messageBrokerRegistry.enableSimpleBroker("/topic","/queue","/user");
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
        messageBrokerRegistry.setUserDestinationPrefix("/user");
    }
}
