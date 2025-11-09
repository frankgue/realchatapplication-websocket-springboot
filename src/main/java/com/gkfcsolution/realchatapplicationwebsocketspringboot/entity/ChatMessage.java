package com.gkfcsolution.realchatapplicationwebsocketspringboot.entity;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created on 2025 at 08:43
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_messages")
public class ChatMessage   extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String sender;
    private String recipient;
    private String color;
    @Column(nullable = false)
    private LocalDateTime timeStamp;
    @Enumerated(EnumType.STRING)
    private MessageType type;
}
