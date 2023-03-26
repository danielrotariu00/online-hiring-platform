package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICompanyRecruiterService;
import com.licenta.databasemicroservice.business.model.CompanyRecruiterDTO;
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
public class CompanyRecruiterController {

    @Autowired
    private ICompanyRecruiterService companyRecruiterService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/company-recruiters", method=RequestMethod.POST)
    public CompanyRecruiterDTO addCompanyRecruiter(@Valid @RequestBody CompanyRecruiterDTO request) {

        return companyRecruiterService.addCompanyRecruiter(request);
    }

    @RequestMapping(value="/companies/{companyId}/company-recruiters", method=RequestMethod.GET)
    public Iterable<CompanyRecruiterDTO> getCompanyIndustriesByCompany(@Min(1) @PathVariable Long companyId) {

        return companyRecruiterService.getCompanyRecruitersByCompany(companyId);
    }

    @RequestMapping(value="/companies/{companyId}/recruiters/{recruiterId}", method=RequestMethod.DELETE)
    public void deleteCompanyIndustry(@PathVariable Long companyId, @PathVariable Long recruiterId) {

        companyRecruiterService.deleteCompanyRecruiter(companyId, recruiterId);
    }
}