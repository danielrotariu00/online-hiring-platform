package com.licenta.database.presentation.controllers;

import com.licenta.database.business.interfaces.ICompanyFollowerService;
import com.licenta.database.business.models.companyfollower.CompanyFollowerRequest;
import com.licenta.database.business.models.company.CompanyResponse;
import com.licenta.database.business.models.user.UserResponse;
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
public class CompanyFollowerController {

    @Autowired
    private ICompanyFollowerService companyFollowerService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/company-followers", method=RequestMethod.POST)
    public void addCompanyFollower(@Valid @RequestBody CompanyFollowerRequest request) {

        companyFollowerService.addCompanyFollower(request);
    }

    @RequestMapping(value="/users/{id}/followed-companies",method=RequestMethod.GET)
    public Iterable<CompanyResponse> getCompaniesFollowedByUser(@NotEmpty @PathVariable String id) {

        return companyFollowerService.getCompaniesFollowedByUser(id);
    }

    @RequestMapping(value="/companies/{id}/followers",method=RequestMethod.GET)
    public Iterable<UserResponse> getCompanyFollowers(@NotEmpty @PathVariable String id) {

        return companyFollowerService.getCompanyFollowers(id);
    }

    @RequestMapping(value="/company-followers", method=RequestMethod.DELETE)
    public void removeCompanyFollower(@Valid @RequestBody CompanyFollowerRequest request) {

        companyFollowerService.removeCompanyFollower(request);
    }
}