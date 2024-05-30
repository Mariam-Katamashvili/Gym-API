package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvili.gym.dto.securityDto.TokenDTO;
import com.mariamkatamashvili.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvili.gym.entity.Token;
import com.mariamkatamashvili.gym.repository.TokenRepository;
import com.mariamkatamashvili.gym.security.GymUserDetails;
import com.mariamkatamashvili.gym.security.JwtTokenGenerator;
import com.mariamkatamashvili.gym.service.LoginAttemptsService;
import com.mariamkatamashvili.gym.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final LoginAttemptsService loginAttemptsService;

    @Override
    public RegistrationResponseDTO register(GymUserDetails user, String username, String password) {
        String token = jwtTokenGenerator.generateToken(user);

        Token tokenEntity = Token.builder()
                .jwtToken(token)
                .user(user.getUser())
                .build();
        tokenEntity = tokenRepository.save(tokenEntity);

        return new RegistrationResponseDTO(
                new LoginRequestDTO(username, password),
                new TokenDTO(
                        tokenEntity.getId(),
                        token,
                        jwtTokenGenerator.extractUsername(token),
                        jwtTokenGenerator.getExpiration(token)
                )
        );
    }
}