package com.gkfcsolution.realchatapplicationwebsocketspringboot.jwt;

import com.gkfcsolution.realchatapplicationwebsocketspringboot.repository.UserRepository;
import com.gkfcsolution.realchatapplicationwebsocketspringboot.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 2025 at 13:49
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 13:49
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = null;
        Long userId = null;

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7);
        }

        // If token is null, need to check cookie
        if (jwtToken == null){
            Cookie[] cookies = request.getCookies();
            if (cookies != null){
                for (Cookie cookie : cookies){
                    if ("JWT".equals(cookie.getName())){
                        jwtToken = cookie.getName();
                        break;
                    }
                }
            }
        }

        userId = jwtService.extractUserId(jwtToken);

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var userDetails = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            if(jwtService.isTokenValid(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            
        }
        filterChain.doFilter(request, response);
        return;
    }
}
