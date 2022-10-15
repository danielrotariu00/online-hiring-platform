package com.licenta.database.presentation.controllers;

import com.licenta.database.business.interfaces.IUserDetailsService;
import com.licenta.database.business.models.userdetails.SaveUserDetailsRequest;
import com.licenta.database.business.models.userdetails.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping(value="/users/{userId}/details")
public class UserDetailsController {

    @Autowired
    private IUserDetailsService userDetailsService;

    @RequestMapping(method=RequestMethod.PUT)
    public void saveUserDetails(@NotEmpty @PathVariable String userId, @Valid @RequestBody SaveUserDetailsRequest request) {

        userDetailsService.saveUserDetails(userId, request);
    }

    @RequestMapping(method=RequestMethod.GET)
    public UserDetailsResponse getUserDetails(@NotEmpty @PathVariable String userId) {

        return userDetailsService.getUserDetails(userId);
    }
}