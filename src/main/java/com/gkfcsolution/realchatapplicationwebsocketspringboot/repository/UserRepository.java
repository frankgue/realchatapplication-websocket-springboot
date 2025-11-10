package com.gkfcsolution.realchatapplicationwebsocketspringboot.repository;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created on 2025 at 09:55
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 09:55
 */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isOnline = :isOnline WHERE u.username = :username")
    void updateUserOnlineStatus(@Param("username") String username, @Param("isOnline") boolean isOnline);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.isOnline = 'true'")
    List<User> findByIsOnlineTrue();
}
