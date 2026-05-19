package com.nanashe.backend.kafka.event;

import com.nanashe.backend.dto.alternatives.response.AlternativeResponseDto;
import com.nanashe.backend.entity.enums.PricingModel;

import java.util.List;

public record KafkaAlternativesEvent(
        List<String> aliases,
        String productName,
        String productCategory,
        String productCountry,
        List<PricingModel> pricingModels,
        List<AlternativeResponseDto> alternatives
) {
    public boolean hasAliases() {
        return aliases != null && !aliases.isEmpty();
    }

    public boolean hasAlternatives() {
        return alternatives != null && !alternatives.isEmpty();
    }
}
