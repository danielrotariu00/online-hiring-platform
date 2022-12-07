package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICompanyService;
import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.company.CreateCompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/companies")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    public void createCompany (@Valid @RequestBody CreateCompanyRequest request) {

        companyService.createCompany(request);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<CompanyResponse> getAllCompanies() {

        return companyService.getCompanies();
    }

    @RequestMapping(value="/{companyId}", method=RequestMethod.GET)
    public CompanyResponse getCompany(@Min(1) @PathVariable Long companyId) {

        return companyService.getCompany(companyId);
    }

    @RequestMapping(value="/{companyId}", method=RequestMethod.DELETE)
    public void deleteCompany(@Min(1) @PathVariable Long companyId) {

        companyService.deleteCompany(companyId);
    }
}