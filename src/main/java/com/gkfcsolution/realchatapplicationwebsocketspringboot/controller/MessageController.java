package com.gkfcsolution.realchatapplicationwebsocketspringboot.controller;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.ChatMessage;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 2025 at 08:49
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:49
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private final ChatService chatService;

    public ResponseEntity<List<ChatMessage>> getPrivateMessages(@RequestParam String user1, @RequestParam String user2){
        List<ChatMessage> messages = chatService.findPrivateMessagesBetweenTwoUsers(user1, user2);
        return ResponseEntity.ok(messages);
    }
}
