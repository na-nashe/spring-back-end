package com.nanashe.backend.dto.review.response;

import com.nanashe.backend.dto.alternatives.response.AlternativeResponseDto;

import java.util.List;

public record ReviewsPageResponseDto(
        AlternativeResponseDto alternative,
        Double averageRating,
        Integer totalReviews,
        List<ReviewResponseDto> reviews
) {}
