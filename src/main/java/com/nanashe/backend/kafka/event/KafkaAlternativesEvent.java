package com.nanashe.backend.kafka.event;

import com.nanashe.backend.dto.kafka.KafkaAlternativeItemDto;

import java.util.List;

public record KafkaAlternativesEvent(
        List<String> aliases,
        List<KafkaAlternativeItemDto> alternatives
) {
}
