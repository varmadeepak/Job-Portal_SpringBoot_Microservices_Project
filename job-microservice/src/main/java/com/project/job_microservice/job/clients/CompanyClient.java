package com.project.job_microservice.job.clients;


import com.project.job_microservice.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANY-SERVICE",url = "${company-service-url}")
public interface CompanyClient {
    @GetMapping("/companies/{companyId}")
    Company getCompany(@PathVariable Long companyId);
}
