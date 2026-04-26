package com.nanashe.backend.service;

import com.nanashe.backend.entity.RefreshToken;
import com.nanashe.backend.entity.User;
import com.nanashe.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.token.expiration.time}")
    private long refreshTokenExpirationTime;

    public String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .user(user)
                .expiredAt(OffsetDateTime.now().plusSeconds(refreshTokenExpirationTime))
                .build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }
}
