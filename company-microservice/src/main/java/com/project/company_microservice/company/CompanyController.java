package com.project.company_microservice.company;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company company = companyService.getCompanyById(id);
        if(company != null)
                return new ResponseEntity<>(company,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company){
        companyService.addJob(company);
        return new ResponseEntity<>("Company added", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatedCompany(@RequestBody Company company,@PathVariable Long id){
        if(companyService.updatedCompany(company,id))
            return new ResponseEntity<>("Company Updated",HttpStatus.OK);
        return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        if(companyService.deleteCompany(id))
            return new ResponseEntity<>("Company Deleted",HttpStatus.OK);
        return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND);
    }

}
