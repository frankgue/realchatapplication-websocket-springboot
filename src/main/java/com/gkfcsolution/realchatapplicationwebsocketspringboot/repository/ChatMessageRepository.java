package com.gkfcsolution.realchatapplicationwebsocketspringboot.repository;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created on 2025 at 09:25
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 09:25
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.type = 'PRIVATE_MESSAGE' AND ((cm.sender = :user1 AND cm.recipient = :user2) " +
            "OR (cm.sender = :user2 AND cm.recipient = :user1)) ORDER BY cm.timeStamp ASC")
    List<ChatMessage> findPrivateMessagesBetweenTwoUsers(@Param("user1") String user1, @Param("user2") String user2);
}
