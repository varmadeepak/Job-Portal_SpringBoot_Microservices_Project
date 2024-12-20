package com.project.company_microservice.company;

import com.project.company_microservice.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    void addJob(Company company);

    boolean updatedCompany(Company company, Long id);

    Company getCompanyById(Long id);

    boolean deleteCompany(Long id);

    void updatedCompanyRating(ReviewMessage reviewMessage);
}
