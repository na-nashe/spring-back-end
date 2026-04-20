package com.nanashe.backend.controller;

import com.nanashe.backend.dto.auth.request.SignupRequestDto;
import com.nanashe.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequestDto dto) {
        userService.signup(dto);
    }
}
