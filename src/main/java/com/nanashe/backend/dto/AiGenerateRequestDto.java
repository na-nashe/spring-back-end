package com.nanashe.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AiGenerateRequestDto(
        String productName,
        List<String> categories
) {
}
