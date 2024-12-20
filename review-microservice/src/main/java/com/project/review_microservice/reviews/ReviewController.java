package com.project.review_microservice.reviews;

import com.project.review_microservice.reviews.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService,ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody Review review , @RequestParam Long companyId){
        boolean companyExists = reviewService.addReview(review,companyId);
        if(companyExists)
        {
            System.out.println("is exists");
            reviewMessageProducer.sendMessage(review);
            return ResponseEntity.ok("Review Added For the Company");
        }
        return new ResponseEntity<>("Company Does not exists",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review){
        if(reviewService.updateReview(reviewId,review))
            return new ResponseEntity<>("Review Updated",HttpStatus.OK);
        return new ResponseEntity<>("Company or Review not Found",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        if(reviewService.deleteReview(reviewId))
            return new ResponseEntity<>("Review Deleted",HttpStatus.OK);
        return new ResponseEntity<>("Company or Review not Found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public ResponseEntity<Double> getAverageReview(@RequestParam Long companyId){
        List<Review> reviews = reviewService.getAllReviews(companyId);
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
       return new ResponseEntity<>(averageRating,HttpStatus.OK);
    }
}
