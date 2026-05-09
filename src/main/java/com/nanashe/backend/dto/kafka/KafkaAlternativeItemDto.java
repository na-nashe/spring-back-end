package com.nanashe.backend.dto.kafka;

public record KafkaAlternativeItemDto(
        String name,
        String category,
        String country,
        String description,
        String url
) {
}
