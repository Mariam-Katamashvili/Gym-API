package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.securityDto.TokenDTO;
import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.entity.Token;
import com.mariamkatamashvlii.gym.repository.TokenRepository;
import com.mariamkatamashvlii.gym.security.GymUserDetails;
import com.mariamkatamashvlii.gym.security.JwtTokenGenerator;
import com.mariamkatamashvlii.gym.service.LoginAttemptsService;
import com.mariamkatamashvlii.gym.service.TokenService;
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