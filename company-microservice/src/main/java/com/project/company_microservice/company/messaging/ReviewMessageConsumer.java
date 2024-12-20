package com.project.company_microservice.company.messaging;

import com.project.company_microservice.company.CompanyService;
import com.project.company_microservice.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage){
        companyService.updatedCompanyRating(reviewMessage);
    }

}
