package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICompanyManagerService;
import com.licenta.databasemicroservice.business.model.CompanyManagerDTO;
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
@RequestMapping(value="/api/companies/{companyId}/managers")
public class CompanyManagerController {

    @Autowired
    private ICompanyManagerService companyManagerService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/{managerId}", method=RequestMethod.PUT)
    public CompanyManagerDTO addCompanyManager(@PathVariable Long companyId, @PathVariable Long managerId) {

        return companyManagerService.addCompanyManager(companyId, managerId);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<CompanyManagerDTO> getCompanyManagersByCompany(@Min(1) @PathVariable Long companyId) {

        return companyManagerService.getCompanyManagersByCompany(companyId);
    }

    @RequestMapping(value="/{managerId}", method=RequestMethod.DELETE)
    public void deleteCompanyManager(@PathVariable Long companyId, @PathVariable Long managerId) {

        companyManagerService.deleteCompanyManager(companyId, managerId);
    }
}