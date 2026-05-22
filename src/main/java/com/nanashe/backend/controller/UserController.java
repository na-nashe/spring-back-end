package com.nanashe.backend.controller;

import com.nanashe.backend.dto.user.response.UserResponseDto;
import com.nanashe.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/info")
    public UserResponseDto getUserProfile(Authentication authentication) {
        return userService.getUserProfile(authentication);
    }
}

