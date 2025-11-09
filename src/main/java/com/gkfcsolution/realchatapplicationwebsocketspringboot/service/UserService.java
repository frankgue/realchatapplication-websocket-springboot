package com.gkfcsolution.realchatapplicationwebsocketspringboot.service;

/**
 * Created on 2025 at 08:51
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:51
 */
public interface UserService {
    boolean userExists(String username);

    void setUserOnlineStatus(String username, boolean isOnline);
}
