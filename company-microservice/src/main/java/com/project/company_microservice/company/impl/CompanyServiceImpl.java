package com.project.company_microservice.company.impl;


import com.project.company_microservice.company.Company;
import com.project.company_microservice.company.CompanyRepository;
import com.project.company_microservice.company.CompanyService;
import com.project.company_microservice.company.clients.ReviewClient;
import com.project.company_microservice.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    CompanyServiceImpl(CompanyRepository companyRepository,ReviewClient reviewClient){
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void addJob(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean updatedCompany(Company updatedCompany, Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            company.setDescription(updatedCompany.getDescription());
            company.setName(updatedCompany.getName());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteCompany(Long id) {
       try {
           companyRepository.deleteById(id);
           return true;
       }catch (Exception e){
           return false;
       }
    }

    @Override
    public void updatedCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElse(null);
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
        System.out.println("reviewMessage : " + reviewMessage.getRating());
    }
}
