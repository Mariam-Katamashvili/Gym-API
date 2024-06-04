package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.securityDto.LoginAttemptDTO;
import com.mariamkatamashvili.gym.service.LoginAttemptsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptsService {
    @Value("${security.login.max-attempts}")
    private int maxAttempts;
    @Value("${security.login.timeout}")
    private int timeoutSeconds;
    private final ConcurrentHashMap<String, LoginAttemptDTO> loginAttempts = new ConcurrentHashMap<>();

    public void loginFailed(String username) {
        LoginAttemptDTO attempt = loginAttempts
                .getOrDefault(
                        username,
                        new LoginAttemptDTO(username, 0, null)
                );
        attempt.setFailedAttempts(attempt.getFailedAttempts() + 1);
        if (attempt.getFailedAttempts() >= maxAttempts) {
            attempt.setLockoutTime(LocalDateTime.now());
        }
        loginAttempts.put(username, attempt);
    }

    public void loginSucceeded(String username) {
        LoginAttemptDTO attempt = loginAttempts.getOrDefault(username, null);
        if (attempt != null) {
            attempt.setFailedAttempts(0);
            attempt.setLockoutTime(null);
            loginAttempts.put(username, attempt);
        }
    }

    public boolean isLockedOut(String username) {
        LoginAttemptDTO attempt = loginAttempts.getOrDefault(username, null);
        if (attempt != null && attempt.getLockoutTime() != null) {
            Duration duration = Duration.ofSeconds(timeoutSeconds);
            return LocalDateTime.now().isBefore(attempt.getLockoutTime().plus(duration));
        }
        return false;
    }
}