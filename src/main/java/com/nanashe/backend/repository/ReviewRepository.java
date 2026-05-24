package com.nanashe.backend.repository;

import com.nanashe.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByAlternativeIdOrderByTimestampDesc(Integer alternativeId);

    boolean existsByUserIdAndAlternativeId(UUID userId, Integer alternativeId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.alternative.id = :altId")
    Double findAverageRatingByAlternativeId(@Param("altId") Integer altId);

    long countByAlternativeId(Integer alternativeId);
}
