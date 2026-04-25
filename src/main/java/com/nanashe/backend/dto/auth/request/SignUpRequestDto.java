package com.nanashe.backend.dto.auth.request;

public record SignUpRequestDto(
        String username,
        String email,
        String password
) {
}
