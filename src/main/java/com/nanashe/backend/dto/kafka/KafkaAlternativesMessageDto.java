package com.nanashe.backend.dto.kafka;

import java.util.List;

public record KafkaAlternativesMessageDto(
        List<String> aliases,
        List<KafkaAlternativeItemDto> alternatives
) {
}
