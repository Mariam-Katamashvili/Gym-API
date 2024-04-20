package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.AuthenticationException;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.security.JwtTokenGenerator;
import com.mariamkatamashvlii.gym.service.UserService;
import com.mariamkatamashvlii.gym.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    public String login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenGenerator.generateJwtToken(authentication);
    }

    @Override
    @Transactional
    public void changePassword(NewPasswordRequestDTO newPasswordRequest) {
        String transactionId = UUID.randomUUID().toString();
        validator.validateUserExists(newPasswordRequest.getUsername());
        log.info("[{}] Starting password change operation for user: {}", transactionId, newPasswordRequest.getUsername());

        User user = userRepo.findByUsername(newPasswordRequest.getUsername());
        if (!passwordEncoder.matches(newPasswordRequest.getCurrentPass(), user.getPassword())) {
            log.warn("[{}] Invalid username or password", transactionId);
            throw new AuthenticationException("Current password is incorrect!");
        }
        user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPass()));
        userRepo.save(user);

        log.info("[{}] Password changed successfully for user: {}", transactionId, newPasswordRequest.getUsername());
    }

}