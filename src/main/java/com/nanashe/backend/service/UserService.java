package com.nanashe.backend.service;

import com.nanashe.backend.dto.auth.request.SignInRequestDto;
import com.nanashe.backend.dto.auth.request.SignUpRequestDto;
import com.nanashe.backend.dto.user.response.UserResponseDto;
import com.nanashe.backend.entity.User;
import com.nanashe.backend.model.SignInResult;
import com.nanashe.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;

    public void signUp(SignUpRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Аккаунт з такою електронною поштою вже існує");
        }
        if (userRepository.existsByUsername(dto.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Аккаунт з таким ім'я вже існує");
        }
        String token = UUID.randomUUID().toString();
        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .joined(OffsetDateTime.now())
                .emailVerified(false)
                .verificationToken(token)
                .verificationTokenExpiresAt(OffsetDateTime.now().plusHours(24))
                .build();
        userRepository.save(user);
        emailService.sendVerificationEmail(dto.email(), token);
    }

    public SignInResult signIn(SignInRequestDto dto) {
        User user = userRepository.findByEmail(dto.email())
                .filter(u -> passwordEncoder.matches(dto.password(), u.getPasswordHash()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неправильний логін або пароль"));

        if (!user.isEmailVerified()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Будь ласка, підтвердіть свою електронну пошту");
        }

        return createTokenPair(user);
    }

    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid verification token"));

        if (user.getVerificationTokenExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verification token has expired");
        }

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiresAt(null);
        userRepository.save(user);
    }

    public String refreshAccessToken(String refreshToken) {
        return jwtService.buildAccessToken(
                refreshTokenService.validateRefreshToken(refreshToken));
    }

    public UserResponseDto getUserProfile(Authentication authentication) {
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        UUID userId = UUID.fromString(authentication.getName());
        return userRepository.findById(userId)
                .map(u -> new UserResponseDto(u.getId(), u.getUsername(), u.getEmail(), u.getAvatar(), u.getJoined()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private SignInResult createTokenPair(User user) {
        String accessToken = jwtService.buildAccessToken(user.getId());
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return new SignInResult(accessToken, refreshToken);
    }
}
