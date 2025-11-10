package com.gkfcsolution.realchatapplicationwebsocketspringboot.service.impl;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.LoginRequestDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.LoginResponseDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.RegisterRequestDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.UserDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.User;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.jwt.JwtService;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.repository.UserRepository;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2025 at 08:52
 * File: AuthenticationServiceImpl.java.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 08:52
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager  authenticationManager;
    private final JwtService jwtService;
    @Override
    public UserDTO signup(RegisterRequestDTO registerRequestDTO) {
       if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()){
           throw new RuntimeException("Username is already in use");
       }
       User user = User.builder()
               .username(registerRequestDTO.getUsername())
               .email(registerRequestDTO.getEmail())
               .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
               .build();
       User savedUser = userRepository.save(user);
       return convertToUserDTO(user);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername()).orElseThrow(() -> new RuntimeException("Username not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        String jwtToken = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .token(jwtToken)
                .userDTO(convertToUserDTO(user))
                .build();

    }

    @Override
    public ResponseEntity<String> logout() {
        ResponseCookie responseCookie = ResponseCookie.from("JWT", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body("Logged out successfully");
    }

    @Override
    public Map<String, Object> getOnlineUsers() {
        List<User> userList = userRepository.findByIsOnlineTrue();
        Map<String, Object> onlineUsers = userList.stream().collect(Collectors.toMap(User::getUsername, user -> user));
        return onlineUsers;
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .isOnline(user.isOnline())
                .build();

        return userDTO;
    }
}
