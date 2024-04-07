package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.AuthenticationException;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.UserService;
import com.mariamkatamashvlii.gym.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public boolean login(LoginRequestDTO loginRequestDTO) {
        String transactionId = UUID.randomUUID().toString();

        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();
        validator.validateUserExists(username);
        log.info("[{}] Transaction started for login operation", transactionId);

        User user = userRepo.findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.info("[{}] Password for {} is incorrect", transactionId, username);
            throw new AuthenticationException("Password is incorrect.");
        }
        log.info("[{}] User {} logged in successfully", transactionId, username);
        return true;

    }

    @Override
    @Transactional
    public void changePassword(NewPasswordRequestDTO newPasswordRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        validator.validateUserExists(newPasswordRequestDTO.getUsername());
        log.info("[{}] Starting password change operation for user: {}", transactionId, newPasswordRequestDTO.getUsername());

        User user = userRepo.findByUsername(newPasswordRequestDTO.getUsername());
        if (!passwordEncoder.matches(newPasswordRequestDTO.getCurrentPass(), user.getPassword())) {
            log.warn("[{}] Invalid username or password", transactionId);
            throw new AuthenticationException("Current password is incorrect!");
        }
        user.setPassword(passwordEncoder.encode(newPasswordRequestDTO.getNewPass()));
        userRepo.save(user);

        log.info("[{}] Password changed successfully for user: {}", transactionId, newPasswordRequestDTO.getUsername());
    }

}