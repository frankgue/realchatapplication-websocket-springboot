package com.gkfcsolution.realchatapplicationwebsocketspringboot.controller;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.LoginRequestDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.LoginResponseDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.RegisterRequestDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.dto.UserDTO;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.entity.User;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.AuthenticationService;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
@RequestMapping("/auth")
public class AuthControlller {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping("/getonlineusers")
    public ResponseEntity<Map<String, Object>> getOnlineUsers(){
        return ResponseEntity.ok(authenticationService.getOnlineUsers());
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(authenticationService.signup(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO loginResponseDTO = authenticationService.login(loginRequestDTO);
        ResponseCookie responseCookie = ResponseCookie.from("JWT", loginResponseDTO.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(1*60*60) // 1 Hour
                .sameSite("strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginResponseDTO.getUserDTO());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return authenticationService.logout();
    }

    @GetMapping("/getcurrentuser")
    public ResponseEntity<?> getCurrentUser(Authentication authentication){
        if (authentication == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("USER NOT AUTHORIZED");
        }
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(convertToUserDTO(user));
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
