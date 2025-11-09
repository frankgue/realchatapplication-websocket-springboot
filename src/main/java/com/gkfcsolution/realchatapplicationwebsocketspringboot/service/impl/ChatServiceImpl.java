package com.gkfcsolution.realchatapplicationwebsocketspringboot.service.impl;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.ChatMessage;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.repository.ChatMessageRepository;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2025 at 09:25
 * File: ChatServiceImpl.java.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 09:25
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> findPrivateMessagesBetweenTwoUsers(String user1, String user2) {
        return chatMessageRepository.findPrivateMessagesBetweenTwoUsers(user1, user2);
    }
}
