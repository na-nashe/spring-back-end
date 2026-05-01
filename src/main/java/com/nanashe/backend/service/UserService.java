package com.nanashe.backend.service;

import com.nanashe.backend.dto.auth.request.SignInRequestDto;
import com.nanashe.backend.model.SignInResult;
import com.nanashe.backend.dto.auth.request.SignUpRequestDto;
import com.nanashe.backend.entity.User;
import com.nanashe.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public void signUp(SignUpRequestDto dto) {
        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .joined(OffsetDateTime.now())
                .build();
        userRepository.save(user);
    }

    public SignInResult signIn(SignInRequestDto dto) {
        return userRepository.findByEmail(dto.email())
                .filter(u -> passwordEncoder.matches(dto.password(), u.getPasswordHash()))
                .map(this::createTokenPair)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
    }

    public String refreshAccessToken(String refreshToken) {
        return "Bearer " + jwtService.generateAccessToken(
                refreshTokenService.validateRefreshToken(refreshToken));
    }

    private SignInResult createTokenPair(User user) {
        String accessToken = "Bearer " + jwtService.generateAccessToken(user.getId());
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return new SignInResult(accessToken, refreshToken);
    }
}