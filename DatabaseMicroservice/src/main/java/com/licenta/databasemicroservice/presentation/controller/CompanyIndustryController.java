package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICompanyIndustryService;
import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryRequest;
import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryResponse;
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
public class CompanyIndustryController {

    @Autowired
    private ICompanyIndustryService companyIndustryService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/company-industries", method=RequestMethod.POST)
    public void addCompanyIndustry(@Valid @RequestBody CompanyIndustryRequest request) {

        companyIndustryService.addCompanyIndustry(request);
    }

    @RequestMapping(value="/companies/{companyId}/company-industries", method=RequestMethod.GET)
    public Iterable<CompanyIndustryResponse> getCompanyIndustriesByCompany(@Min(1) @PathVariable Long companyId) {

        return companyIndustryService.getCompanyIndustriesByCompany(companyId);
    }

    @RequestMapping(value="/industries/{industryId}/companies", method=RequestMethod.GET)
    public Iterable<CompanyResponse> getCompaniesByIndustry(@Min(1) @PathVariable Integer industryId) {

        return companyIndustryService.getCompaniesByIndustry(industryId);
    }

    @RequestMapping(value="/company-industries", method=RequestMethod.DELETE)
    public void deleteCompanyIndustry(@Valid @RequestBody CompanyIndustryRequest request) {

        companyIndustryService.deleteCompanyIndustry(request);
    }
}