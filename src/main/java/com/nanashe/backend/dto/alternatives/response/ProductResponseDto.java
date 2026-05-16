package com.nanashe.backend.dto.alternatives.response;

import java.util.List;

public record ProductResponseDto(
        Integer id,
        String name,
        String category,
        String origin,
        List<String> aliases,
        List<AlternativeResponseDto> alternatives
) {}
