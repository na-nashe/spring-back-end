package com.nanashe.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AiAlternativeDto(
        String name,
        String category,
        @JsonProperty("pricing_model") String pricingModel,
        String description,
        String url,
        String country
) {
}
