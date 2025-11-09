package com.gkfcsolution.realchatapplicationwebsocketspringboot.service.impl;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.repository.UserRepository;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created on 2025 at 08:51
 * File: UserServiceImpl.java.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:51
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void setUserOnlineStatus(String username, boolean isOnline) {
        userRepository.updateUserOnlineStatus(username, isOnline);
    }
}
