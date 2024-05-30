package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.securityDto.TokenDTO;
import com.mariamkatamashvili.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvili.gym.dto.userDto.NewPasswordRequestDTO;
import com.mariamkatamashvili.gym.entity.Token;
import com.mariamkatamashvili.gym.entity.User;
import com.mariamkatamashvili.gym.exception.GymException;
import com.mariamkatamashvili.gym.repository.TokenRepository;
import com.mariamkatamashvili.gym.security.GymUserDetails;
import com.mariamkatamashvili.gym.service.LoginAttemptsService;
import com.mariamkatamashvili.gym.service.UserService;
import com.mariamkatamashvili.gym.repository.UserRepository;
import com.mariamkatamashvili.gym.security.JwtTokenGenerator;
import com.mariamkatamashvili.gym.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final TokenRepository tokenRepository;
    private final LoginAttemptsService loginAttemptsService;

    @Override
    public TokenDTO login(LoginRequestDTO loginRequest) {
        String username = loginRequest.getUsername();

        checkLock(username);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            loginRequest.getPassword()
                    )
            );
            loginAttemptsService.loginSucceeded(username);
        } catch (AuthenticationException e) {
            loginAttemptsService.loginFailed(username);
            throw new GymException("Wrong credentials.");
        }

        User user = userRepo
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new GymException("User not found"));

        return generateTokenDTO(user);
    }

    @Override
    @Transactional
    public TokenDTO changePassword(NewPasswordRequestDTO newPasswordRequest) {
        validator.validateUserExists(newPasswordRequest.getUsername());

        String username = newPasswordRequest.getUsername();
        checkLock(username);
        User user = userRepo.findByUsername(newPasswordRequest.getUsername())
                .orElseThrow(() -> new GymException("User not found"));
        if (!passwordEncoder.matches(newPasswordRequest.getCurrentPass(), user.getPassword())) {
            loginAttemptsService.loginFailed(username);
            throw new GymException("Current password is incorrect!");
        }

        loginAttemptsService.loginSucceeded(username);
        user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPass()));
        userRepo.save(user);

        return generateTokenDTO(user);
    }

    private TokenDTO generateTokenDTO(User userEntity) {
        GymUserDetails user = new GymUserDetails(userEntity);

        String token = jwtTokenGenerator.generateToken(user);

        List<Token> tokens = tokenRepository.findByUserId(userEntity.getId());
        if (!tokens.isEmpty()) {
            tokenRepository.deleteAll(tokens);
        }
        Token tokenEntity = Token
                .builder()
                .user(userEntity)
                .jwtToken(token)
                .build();
        tokenEntity = tokenRepository.save(tokenEntity);

        return TokenDTO.builder()
                .id(tokenEntity.getId())
                .token(token)
                .username(jwtTokenGenerator.extractUsername(token))
                .expiredAt(jwtTokenGenerator.getExpiration(token))
                .build();
    }

    private void checkLock(String username) {
        if (loginAttemptsService.isLockedOut(username)) {
            throw new GymException("Account is locked. Try again later.");
        }
    }
}