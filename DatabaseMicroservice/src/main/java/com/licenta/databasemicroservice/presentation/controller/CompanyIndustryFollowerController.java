package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICompanyIndustryFollowerService;
import com.licenta.databasemicroservice.business.model.CompanyIndustryDTO;
import com.licenta.databasemicroservice.business.model.UserDTO;
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
@RequestMapping(value="/api")
public class CompanyIndustryFollowerController {

    @Autowired
    private ICompanyIndustryFollowerService companyIndustryFollowerService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/users/{userId}/followed-company-industries/{companyIndustryId}", method=RequestMethod.PUT)
    public void addCompanyFollower(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long companyIndustryId) {

        companyIndustryFollowerService.addCompanyIndustryFollower(userId, companyIndustryId);
    }

    @RequestMapping(value="/users/{userId}/followed-company-industries", method=RequestMethod.GET)
    public Iterable<CompanyIndustryDTO> getCompanyIndustriesFollowedByUser(@Min(1) @PathVariable Long userId) {

        return companyIndustryFollowerService.getFollowedCompanyIndustries(userId);
    }

    @RequestMapping(value="/company-industries/{companyIndustryId}/followers", method=RequestMethod.GET)
    public Iterable<UserDTO> getCompanyIndustryFollowers(@Min(1) @PathVariable Long companyIndustryId) {

        return companyIndustryFollowerService.getCompanyIndustryFollowers(companyIndustryId);
    }

    @RequestMapping(value="/users/{userId}/followed-company-industries/{companyIndustryId}", method=RequestMethod.DELETE)
    public void removeCompanyIndustryFollower(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long companyIndustryId) {

        companyIndustryFollowerService.removeCompanyIndustryFollower(userId, companyIndustryId);
    }

    @RequestMapping(value="/users/{userId}/followed-company-industries", method=RequestMethod.DELETE)
    public void removeFollowedCompanyIndustries(@Min(1) @PathVariable Long userId) {

        companyIndustryFollowerService.removeFollowedCompanyIndustries(userId);
    }
}