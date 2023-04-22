package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICompanyService;
import com.licenta.databasemicroservice.business.model.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/api/companies")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    public void createCompany (@Valid @RequestBody CompanyDTO request) {

        companyService.createCompany(request);
    }

    @RequestMapping(value="/{companyId}", method=RequestMethod.PUT)
    public void updateCompany (@PathVariable Long companyId, @Valid @RequestBody CompanyDTO request) {

        companyService.updateCompany(companyId, request);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<CompanyDTO> getAllCompanies() {

        return companyService.getCompanies();
    }

    @RequestMapping(value="/{companyId}", method=RequestMethod.GET)
    public CompanyDTO getCompany(@Min(1) @PathVariable Long companyId) {

        return companyService.getCompany(companyId);
    }

    @RequestMapping(value="/{companyId}", method=RequestMethod.DELETE)
    public void deleteCompany(@Min(1) @PathVariable Long companyId) {

        companyService.deleteCompany(companyId);
    }

    @RequestMapping(value = "/{companyId}/image", method=RequestMethod.PUT)
    public void uploadImage(@PathVariable Long companyId, @RequestParam("img") MultipartFile image) throws IOException {

        companyService.saveImage(companyId, image);
    }
}