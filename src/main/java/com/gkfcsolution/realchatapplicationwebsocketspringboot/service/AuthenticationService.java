package com.gkfcsolution.realchatapplicationwebsocketspringboot.service;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.LoginRequestDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.LoginResponseDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.RegisterRequestDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created on 2025 at 08:50
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:50
 */
public interface AuthenticationService {

    UserDTO signup(RegisterRequestDTO registerRequestDTO);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    ResponseEntity<String> logout();

    Map<String, Object> getOnlineUsers();
}
