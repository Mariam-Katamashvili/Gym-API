package com.mariamkatamashvlii.gym.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BlockCheckFilter extends OncePerRequestFilter {

    private final LoginAttemptService loginAttemptService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        if (loginAttemptService.isBlocked(ip)) {
            response.setStatus(429);
            response.getWriter().write("You are temporarily blocked due to too many failed login attempts.");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
