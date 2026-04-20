package com.nanashe.backend.service;

import com.nanashe.backend.dto.auth.request.SignupRequestDto;
import com.nanashe.backend.entity.User;
import com.nanashe.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto dto) {
        userRepository.save(new User(dto, passwordEncoder.encode(dto.password())));
    }
}
