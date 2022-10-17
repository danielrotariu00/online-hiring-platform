package com.licenta.database.presentation.controllers;

import com.licenta.database.business.interfaces.ICompanyIndustryFollowerService;
import com.licenta.database.business.model.companyindustry.CompanyIndustryResponse;
import com.licenta.database.business.model.companyindustryfollower.CompanyIndustryFollowerRequest;
import com.licenta.database.business.model.user.UserResponse;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Validated
@RestController
public class CompanyIndustryFollowerController {

    @Autowired
    private ICompanyIndustryFollowerService companyIndustryFollowerService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/company-industry-followers", method=RequestMethod.POST)
    public void addCompanyFollower(@Valid @RequestBody CompanyIndustryFollowerRequest request) {

        companyIndustryFollowerService.addCompanyIndustryFollower(request);
    }

    @RequestMapping(value="/users/{userId}/followed-company-industries",method=RequestMethod.GET)
    public Iterable<CompanyIndustryResponse> getCompanyIndustriesFollowedByUser(@NotEmpty @PathVariable String userId) {

        return companyIndustryFollowerService.getFollowedCompanyIndustries(userId);
    }

    @RequestMapping(value="/company-industry/{companyIndustryId}/followers",method=RequestMethod.GET)
    public Iterable<UserResponse> getCompanyIndustryFollowers(@Min(1) @PathVariable Integer companyIndustryId) {

        return companyIndustryFollowerService.getCompanyIndustryFollowers(companyIndustryId);
    }

    @RequestMapping(value="/company-industry-followers", method=RequestMethod.DELETE)
    public void removeCompanyIndustryFollower(@Valid @RequestBody CompanyIndustryFollowerRequest request) {

        companyIndustryFollowerService.removeCompanyIndustryFollower(request);
    }
}