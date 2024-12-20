package com.project.review_microservice.reviews;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);

    boolean addReview(Review review, Long companyId);

    Review getReviewById(Long reviewId);

    boolean updateReview(Long reviewId, Review review);

    boolean deleteReview(Long reviewId);
}
