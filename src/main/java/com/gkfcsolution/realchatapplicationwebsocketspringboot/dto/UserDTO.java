package com.gkfcsolution.realchatapplicationwebsocketspringboot.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

/**
 * Created on 2025 at 13:48
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 13:48
 */
@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private boolean  isOnline;
}
