package com.gkfcsolution.realchatapplicationwebsocketspringboot.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created on 2025 at 13:47
 * File: null.java
 * Project: realchatapplication-websocket-springboot
 *
 * @author Frank GUEKENG
 * @date 09/11/2025
 * @time 13:47
 */
@Data
@Builder
public class LoginRequestDTO {
    private String username;
    private String password;
}
