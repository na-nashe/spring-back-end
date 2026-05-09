package com.nanashe.backend.kafka.event;

import com.nanashe.backend.dto.alternatives.response.AiAlternativeResponseDto;

import java.util.List;

public record KafkaAlternativesEvent(
        List<String> aliases,
        List<AiAlternativeResponseDto> alternatives
) {
}
