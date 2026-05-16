package com.nanashe.backend.dto.alternatives.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlternativeSearchResponseDto(
        String message,
        String productName,
        List<AlternativeResponseDto> alternatives
) {
}
