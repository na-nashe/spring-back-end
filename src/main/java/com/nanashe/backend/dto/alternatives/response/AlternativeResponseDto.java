package com.nanashe.backend.dto.alternatives.response;

public record AlternativeResponseDto(
        String name,
        String description,
        String url,
        String country
) {
}
