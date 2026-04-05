package com.nanashe.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AiAlternativeDto(
        String name,
        String category,
        String description,
        String url,
        String country
) {
}
