package com.nanashe.backend.dto.review.request;

import java.util.List;

public record ReviewRequestDto(
        Short rating,
        String title,
        String content,
        List<String> pros,
        List<String> cons
) {}
