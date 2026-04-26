package com.nanashe.backend.dto.auth.request;

public record SignInResult(String accessToken, String refreshToken) {
}
