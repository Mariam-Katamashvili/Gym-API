package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.userDto.LoginDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordDTO;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    @Override
    @Transactional
    public boolean login(LoginDTO loginDTO) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Transaction started for login operation", transactionId);

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        try {
            User user = userRepo.findUserByUsername(username);
            if (user == null) {
                log.info("[{}] Username {} does not exist", transactionId, username);
                return false;
            }
            if (user.getPassword().equals(password)) {
                log.info("[{}] User {} logged in successfully", transactionId, username);
                return true;
            } else {
                log.info("[{}] Password for {} is incorrect", transactionId, username);
                return false;
            }
        } catch (Exception e) {
            log.error("[{}] Error during login for user {}: {}", transactionId, username, e.getMessage());
            throw e;
        } finally {
            log.info("[{}] Transaction ended for login operation", transactionId);
        }
    }

    @Override
    public void changePassword(NewPasswordDTO newPasswordDTO) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Starting password change operation for user: {}", transactionId, newPasswordDTO.getUsername());
        try {
            User user = userRepo.findUserByUsername(newPasswordDTO.getUsername());
            if (user == null) {
                log.warn("[{}] No user found with username: {}", transactionId, newPasswordDTO.getUsername());
                return;
            }

            user.setPassword(newPasswordDTO.getNewPass());
            userRepo.save(user);

            log.info("[{}] Password changed successfully for user: {}", transactionId, newPasswordDTO.getUsername());
        } catch (Exception e) {
            log.error("[{}] Error changing password for user: {}. Error: {}", transactionId, newPasswordDTO.getUsername(), e.getMessage());
            throw e;
        }
    }

}