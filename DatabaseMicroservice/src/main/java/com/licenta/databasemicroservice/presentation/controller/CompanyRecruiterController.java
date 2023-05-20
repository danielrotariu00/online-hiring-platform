package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICompanyRecruiterService;
import com.licenta.databasemicroservice.business.model.CompanyRecruiterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin
@RequestMapping(value="/api/companies/{companyId}/recruiters")
public class CompanyRecruiterController {

    @Autowired
    private ICompanyRecruiterService companyRecruiterService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/{recruiterId}", method=RequestMethod.PUT)
    public CompanyRecruiterDTO addCompanyRecruiter(@PathVariable Long companyId, @PathVariable Long recruiterId) {
        return companyRecruiterService.addCompanyRecruiter(companyId, recruiterId);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<CompanyRecruiterDTO> getCompanyRecruitersByCompany(@Min(1) @PathVariable Long companyId) {
        return companyRecruiterService.getCompanyRecruitersByCompany(companyId);
    }

    @RequestMapping(value="/{recruiterId}", method=RequestMethod.DELETE)
    public void deleteCompanyRecruiter(@PathVariable Long companyId, @PathVariable Long recruiterId) {
        companyRecruiterService.deleteCompanyRecruiter(companyId, recruiterId);
    }
}