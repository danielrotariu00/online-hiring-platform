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

@RestController
@RequestMapping(value="/users/{userId}/details")
public class UserDetailsController {

    @Autowired
    private IUserDetailsService userDetailsService;

    @RequestMapping(method=RequestMethod.PUT)
    public void saveUserDetails (@PathVariable String userId,
                                @RequestBody SaveUserDetailsRequest request) {

        userDetailsService.saveUserDetails(userId, request);
    }

    @RequestMapping(method=RequestMethod.GET)
    public UserDetailsResponse getUserDetails(@PathVariable String userId) {

        return userDetailsService.getUserDetails(userId);
    }
}