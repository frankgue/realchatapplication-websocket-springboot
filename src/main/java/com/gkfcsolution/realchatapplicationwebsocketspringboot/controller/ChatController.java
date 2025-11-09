package com.gkfcsolution.realchatapplicationwebsocketspringboot.controller;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.ChatMessage;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.enums.MessageType;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.ChatService;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created on 2025 at 08:48
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:48
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        if (userService.userExists(chatMessage.getSender())) {
            // Store username in Session
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
            userService.setUserOnlineStatus(chatMessage.getSender(), true);
            log.info("User added Successfully {} with session ID {}", chatMessage.getSender(), headerAccessor.getSessionId());
            chatMessage.setTimeStamp(LocalDateTime.now());
            if (chatMessage.getContent() == null) {
                chatMessage.setContent("");
            }
            chatMessage.setType(MessageType.CHAT);
            return chatService.saveChatMessage(chatMessage);
        }
        return null;
    }


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        if (userService.userExists(chatMessage.getSender())) {
            if (chatMessage.getTimeStamp() == null) {
                chatMessage.setTimeStamp(LocalDateTime.now());
            }
            if (chatMessage.getContent() == null) {
                chatMessage.setContent("");
            }
            return chatService.saveChatMessage(chatMessage);
        }
        return null;
    }


    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        if (userService.userExists(chatMessage.getSender()) && userService.userExists(chatMessage.getRecipient())) {
            if (chatMessage.getTimeStamp() == null) {
                chatMessage.setTimeStamp(LocalDateTime.now());
            }

            if (chatMessage.getContent() == null) {
                chatMessage.setContent("");
            }

            chatMessage.setType(MessageType.PRIVATE_MESSAGE);

            ChatMessage savedMessage = chatService.saveChatMessage(chatMessage);
            log.info("Message Saved Successfully with ID {}", chatMessage.getId());
            try {

                String recipientDestination = "/user/" + chatMessage.getRecipient() + "/queue/private";
                String senderDestination = "/user/" + chatMessage.getSender() + "/queue/private";

                log.info("Sending message to recipient destination {}", recipientDestination);
                log.info("Sending message to sender destination {}", senderDestination);

                messagingTemplate.convertAndSend(recipientDestination, savedMessage);
                messagingTemplate.convertAndSend(senderDestination, savedMessage);

            } catch (Exception e){
                log.error("Error occured while sending the message {}", e.getMessage());
                e.printStackTrace();
            }
        } else {
            log.error("Error: sender {} or recipient {} does not exist....");
        }
    }
}
