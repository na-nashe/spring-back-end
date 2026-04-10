package com.nanashe.backend.dto.alternatives.response;

public record AiAlternativeResponseDto(
        String name,
        String category,
        String description,
        String url,
        String country
) {
}
