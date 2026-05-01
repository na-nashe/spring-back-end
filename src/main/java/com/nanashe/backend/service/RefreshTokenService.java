package com.nanashe.backend.service;

import com.nanashe.backend.entity.RefreshToken;
import com.nanashe.backend.entity.User;
import com.nanashe.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.token.expiration.time}")
    private long refreshTokenExpirationTime;

    @Transactional(readOnly = true)
    public UUID validateRefreshToken(String token) {

        return refreshTokenRepository.findById(token)
                .filter(rt -> !isTokenExpired(rt))
                .map(RefreshToken::getUser)
                .map(User::getId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    }

    private boolean isTokenExpired(RefreshToken token) {
        return token.getExpiredAt().isBefore(OffsetDateTime.now());
    }

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
