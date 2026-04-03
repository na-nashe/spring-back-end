package com.nanashe.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AiGenerateRequestDto(
        @JsonProperty("productName") String productName,
        @JsonProperty("categories") List<String> categories,
        @JsonProperty("pricing_model") List<String> pricingModel
) {
}
