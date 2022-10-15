package com.licenta.database.presentation.controllers;

import com.licenta.database.business.interfaces.ICompanyService;
import com.licenta.database.business.models.company.CompanyResponse;
import com.licenta.database.business.models.company.CreateCompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Validated
@RestController
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

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public CompanyResponse getUser(@NotEmpty @PathVariable String id) {

        return companyService.getCompany(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void deleteCompany(@NotEmpty @PathVariable String id) {

        companyService.deleteCompany(id);
    }
}