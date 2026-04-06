package com.nanashe.backend.dto;

import java.util.List;

public record AiAlternativeSearchResponseDto(
        String message,
        List<AiAlternativeDto> alternatives
) {
}
