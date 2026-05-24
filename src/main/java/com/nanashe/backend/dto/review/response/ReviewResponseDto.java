package com.nanashe.backend.dto.review.response;

import java.time.OffsetDateTime;
import java.util.List;

public record ReviewResponseDto(
        Integer id,
        String username,
        String avatar,
        Short rating,
        String title,
        String content,
        List<String> pros,
        List<String> cons,
        OffsetDateTime timestamp
) {}
