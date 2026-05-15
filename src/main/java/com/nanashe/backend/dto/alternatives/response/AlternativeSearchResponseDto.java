package com.nanashe.backend.dto.alternatives.response;

import java.util.List;

public record AlternativeSearchResponseDto(
        String message,
        List<AlternativeResponseDto> alternatives
) {
}
