package com.nanashe.backend.service;

import com.nanashe.backend.dto.auth.request.SignInRequestDto;
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

    public void signUp(SignUpRequestDto dto) {
        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .joined(OffsetDateTime.now())
                .build();
        userRepository.save(user);
    }

    public String signIn(SignInRequestDto dto) {
        String token = userRepository.findByEmail(dto.email())
                .filter(user -> passwordEncoder.matches(dto.password(), user.getPasswordHash()))
                .map(User::getId)
                .map(jwtService::generateAccessToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        return "Bearer " + token;
    }
}