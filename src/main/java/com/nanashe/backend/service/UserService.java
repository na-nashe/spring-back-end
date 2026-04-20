package com.nanashe.backend.service;

import com.nanashe.backend.dto.auth.request.SignupRequestDto;
import com.nanashe.backend.entity.User;
import com.nanashe.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto dto) {
        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .joined(OffsetDateTime.now())
                .build();
        userRepository.save(user);
    }
}