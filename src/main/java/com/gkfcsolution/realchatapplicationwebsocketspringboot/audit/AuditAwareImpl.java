package com.gkfcsolution.realchatapplicationwebsocketspringboot.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 2025 at 20:01
 * File: null.java
 * Project: springBoot-department-management
 *
 * @author Frank GUEKENG
 * @date 02/11/2025
 * @time 20:01
 */
@Component("auditorAware")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ADMIN");
    }
}
