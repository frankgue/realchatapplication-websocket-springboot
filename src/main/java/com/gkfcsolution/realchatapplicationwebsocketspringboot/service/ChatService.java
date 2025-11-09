package com.gkfcsolution.realchatapplicationwebsocketspringboot.service;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.ChatMessage;

import java.util.List;

/**
 * Created on 2025 at 09:24
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 09:24
 */
public interface ChatService {
    ChatMessage saveChatMessage(ChatMessage chatMessage);

    List<ChatMessage> findPrivateMessagesBetweenTwoUsers(String user1, String user2);
}
