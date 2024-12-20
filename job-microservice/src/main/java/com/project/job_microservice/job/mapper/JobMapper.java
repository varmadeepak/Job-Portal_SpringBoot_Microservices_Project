package com.project.job_microservice.job.mapper;

import com.project.job_microservice.job.Job;
import com.project.job_microservice.job.dto.JobDTO;
import com.project.job_microservice.job.external.Company;
import com.project.job_microservice.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobCompanyDTO(Job job, Company company, List<Review> reviews) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setCompany(company);
        jobDTO.setId(job.getId());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setReview(reviews);
        return jobDTO;
    }
}
