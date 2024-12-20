package com.project.review_microservice.reviews.messaging;

import com.project.review_microservice.reviews.Review;
import com.project.review_microservice.reviews.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review review){
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(review.getId());
        reviewMessage.setDescription(review.getDescription());
        reviewMessage.setRating(review.getRating());
        reviewMessage.setTitle(review.getTitle());
        reviewMessage.setCompanyId(review.getCompanyId());

        rabbitTemplate.convertAndSend("companyRatingQueue",reviewMessage);
    }
}
