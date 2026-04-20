package com.nanashe.backend.dto.auth.request;

public record SignupRequestDto(
        String username,
        String email,
        String password
) {
}
