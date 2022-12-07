package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IUserDetailsService;
import com.licenta.databasemicroservice.business.model.userdetails.SaveUserDetailsRequest;
import com.licenta.databasemicroservice.business.model.userdetails.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/users/{userId}/details")
public class UserDetailsController {

    @Autowired
    private IUserDetailsService userDetailsService;

    @RequestMapping(method=RequestMethod.PUT)
    public void saveUserDetails(@Min(1) @PathVariable Long userId, @Valid @RequestBody SaveUserDetailsRequest request) {

        userDetailsService.saveUserDetails(userId, request);
    }

    @RequestMapping(method=RequestMethod.GET)
    public UserDetailsResponse getUserDetails(@Min(1) @PathVariable Long userId) {

        return userDetailsService.getUserDetails(userId);
    }
}