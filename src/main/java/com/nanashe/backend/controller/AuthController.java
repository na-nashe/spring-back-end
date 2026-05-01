package com.nanashe.backend.controller;

import com.nanashe.backend.dto.auth.request.SignInRequestDto;
import com.nanashe.backend.dto.auth.request.SignUpRequestDto;
import com.nanashe.backend.model.SignInResult;
import com.nanashe.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Value("${refresh.token.expiration.time}")
    private long refreshTokenExpirationTime;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignUpRequestDto dto) {
        userService.signUp(dto);
    }

    @PostMapping("/accesstoken/refresh")
    public ResponseEntity<Void> refreshAccessToken(
            @CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = userService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .build();
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> signIn(@RequestBody SignInRequestDto dto) {
        SignInResult result = userService.signIn(dto);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", result.refreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(refreshTokenExpirationTime)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, result.accessToken())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }
}
