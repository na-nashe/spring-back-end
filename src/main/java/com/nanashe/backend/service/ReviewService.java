package com.nanashe.backend.service;

import com.nanashe.backend.dto.alternatives.response.AlternativeResponseDto;
import com.nanashe.backend.dto.review.request.ReviewRequestDto;
import com.nanashe.backend.dto.review.response.ReviewResponseDto;
import com.nanashe.backend.dto.review.response.ReviewsPageResponseDto;
import com.nanashe.backend.entity.Alternative;
import com.nanashe.backend.entity.Review;
import com.nanashe.backend.entity.User;
import com.nanashe.backend.repository.AlternativeRepository;
import com.nanashe.backend.repository.ReviewRepository;
import com.nanashe.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AlternativeRepository alternativeRepository;
    private final UserRepository userRepository;

    public ReviewsPageResponseDto getReviews(Integer alternativeId) {
        Alternative alt = alternativeRepository.findById(alternativeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alternative not found"));

        List<Review> reviews = reviewRepository.findByAlternativeIdOrderByTimestampDesc(alternativeId);
        Double avg = reviewRepository.findAverageRatingByAlternativeId(alternativeId);

        AlternativeResponseDto altDto = AlternativeResponseDto.builder()
                .id(alt.getId())
                .name(alt.getName())
                .description(alt.getDescription())
                .url(alt.getUrl())
                .country(alt.getOrigin().getName())
                .pricingModel(alt.getPricingModel() != null ? alt.getPricingModel().name().toLowerCase() : null)
                .build();

        List<ReviewResponseDto> reviewDtos = reviews.stream()
                .map(r -> new ReviewResponseDto(
                        r.getId(),
                        r.getUser().getUsername(),
                        r.getUser().getAvatar(),
                        r.getRating(),
                        r.getTitle(),
                        r.getContent(),
                        r.getPros() != null ? r.getPros() : List.of(),
                        r.getCons() != null ? r.getCons() : List.of(),
                        r.getTimestamp()
                ))
                .toList();

        return new ReviewsPageResponseDto(altDto, avg != null ? avg : 0.0, reviews.size(), reviewDtos);
    }

    public ReviewResponseDto createReview(Integer alternativeId, ReviewRequestDto dto, Authentication authentication) {
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (dto.rating() == null || dto.rating() < 1 || dto.rating() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating must be between 1 and 5");
        }

        UUID userId = UUID.fromString(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Alternative alt = alternativeRepository.findById(alternativeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alternative not found"));

        if (reviewRepository.existsByUserIdAndAlternativeId(userId, alternativeId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already reviewed this alternative");
        }

        Review review = new Review();
        review.setUser(user);
        review.setAlternative(alt);
        review.setRating(dto.rating());
        review.setTitle(dto.title());
        review.setContent(dto.content());
        review.setPros(dto.pros());
        review.setCons(dto.cons());
        review.setTimestamp(OffsetDateTime.now());
        review = reviewRepository.save(review);

        return new ReviewResponseDto(
                review.getId(),
                user.getUsername(),
                user.getAvatar(),
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getPros() != null ? review.getPros() : List.of(),
                review.getCons() != null ? review.getCons() : List.of(),
                review.getTimestamp()
        );
    }
}
