package com.nanashe.backend.controller;

import com.nanashe.backend.dto.user.response.UserResponseDto;
import com.nanashe.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/info")
    public UserResponseDto getUserProfile(Authentication authentication) {
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        UUID userId = UUID.fromString(authentication.getName());
        return userRepository.findById(userId)
                .map(u -> new UserResponseDto(u.getId(), u.getUsername(), u.getEmail(), u.getAvatar(), u.getJoined()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

