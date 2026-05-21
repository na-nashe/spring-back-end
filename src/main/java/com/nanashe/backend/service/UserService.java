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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists");
        }
        if (userRepository.existsByUsername(dto.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This username is already taken");
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!user.isEmailVerified()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Please verify your email before signing in");
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

    private SignInResult createTokenPair(User user) {
        String accessToken = jwtService.buildAccessToken(user.getId());
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return new SignInResult(accessToken, refreshToken);
    }
}
