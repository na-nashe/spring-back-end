package com.nanashe.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AiAlternativeDto(
        @JsonProperty("name") String name,
        @JsonProperty("category") String category,
        @JsonProperty("pricing_model") String pricingModel,
        @JsonProperty("description") String description,
        @JsonProperty("url") String url,
        @JsonProperty("country") String country
) {
}
