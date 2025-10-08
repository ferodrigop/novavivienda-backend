package com.novavivienda.backend.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    /**
     * Allows to globally retrieve the user details in Spring Security
     * through Dependency Injection using a simple facade pattern
     *
     * @return currently authenticated principal
     */
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
