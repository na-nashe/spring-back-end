package com.nanashe.backend.controller;

import com.nanashe.backend.dto.review.request.ReviewRequestDto;
import com.nanashe.backend.dto.review.response.ReviewResponseDto;
import com.nanashe.backend.dto.review.response.ReviewsPageResponseDto;
import com.nanashe.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{alternativeId}")
    public ReviewsPageResponseDto getReviews(@PathVariable Integer alternativeId) {
        return reviewService.getReviews(alternativeId);
    }

    @PostMapping("/{alternativeId}")
    public ReviewResponseDto createReview(
            @PathVariable Integer alternativeId,
            @RequestBody ReviewRequestDto dto,
            Authentication authentication) {
        return reviewService.createReview(alternativeId, dto, authentication);
    }
}
