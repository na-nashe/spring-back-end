package com.nanashe.backend.dto.alternatives.response;

import java.util.List;

public record AiAlternativeSearchResponseDto(
        String message,
        List<AiAlternativeResponseDto> alternatives
) {
}
