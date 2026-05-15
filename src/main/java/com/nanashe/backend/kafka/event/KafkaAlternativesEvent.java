package com.nanashe.backend.kafka.event;

import com.nanashe.backend.dto.alternatives.response.AlternativeResponseDto;

import java.util.List;

public record KafkaAlternativesEvent(
        List<String> aliases,
        String productName,
        String productCategory,
        String productCountry,
        List<AlternativeResponseDto> alternatives
) {
    public boolean hasAliases() {
        return aliases != null && !aliases.isEmpty();
    }

    public boolean hasAlternatives() {
        return alternatives != null && !alternatives.isEmpty();
    }
}
