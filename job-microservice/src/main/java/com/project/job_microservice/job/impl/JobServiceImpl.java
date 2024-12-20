package com.project.job_microservice.job.impl;


import com.project.job_microservice.job.Job;
import com.project.job_microservice.job.JobRepository;
import com.project.job_microservice.job.JobService;
import com.project.job_microservice.job.clients.CompanyClient;
import com.project.job_microservice.job.clients.ReviewClient;
import com.project.job_microservice.job.dto.JobDTO;
import com.project.job_microservice.job.external.Company;
import com.project.job_microservice.job.external.Review;
import com.project.job_microservice.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
//    @Autowired
//    RestTemplate restTemplate;


    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    private JobRepository jobRepository;

    JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    @CircuitBreaker(name = "companyBreaker",
        fallbackMethod = "companyBreakerFallback")
//    @RateLimiter(name = "companyBreaker",
//    fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::mapToDto)
                .toList();
    }
    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Company Service is Down");
        return list;
    }
//    private JobDTO mapToDto(Job job){

    /// /        Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8083/companies/" + job.getCompanyId(),Company.class);
//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEW-SERVICE:8084/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
//        List<Review> reviews = reviewResponse.getBody();
//        return JobMapper.mapToJobCompanyDTO(job,company,reviews);
//    }
    private JobDTO mapToDto(Job job) {

        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        return JobMapper.mapToJobCompanyDTO(job, company, reviews);
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO findJob(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return mapToDto(job);
    }

    @Override
    public boolean deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Job job, Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            Job currJob = optionalJob.get();
            currJob.setDescription(job.getDescription());
            currJob.setTitle(job.getTitle());
            currJob.setLocation(job.getLocation());
            currJob.setMaxSalary(job.getMaxSalary());
            currJob.setMinSalary(job.getMinSalary());
            jobRepository.save(currJob);
            return true;

        }
        return false;
    }
}
