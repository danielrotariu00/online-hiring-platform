package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IUserDetailsService;
import com.licenta.databasemicroservice.business.model.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/api/users/{userId}/details")
public class UserDetailsController {

    @Autowired
    private IUserDetailsService userDetailsService;

    @RequestMapping(method=RequestMethod.PUT)
    public UserDetailsDTO saveUserDetails(@Min(1) @PathVariable Long userId, @Valid @RequestBody UserDetailsDTO request) {

        return userDetailsService.saveUserDetails(userId, request);
    }

    @RequestMapping(method=RequestMethod.GET)
    public UserDetailsDTO getUserDetails(@Min(1) @PathVariable Long userId) {

        return userDetailsService.getUserDetails(userId);
    }

    @RequestMapping(method=RequestMethod.DELETE)
    public void deleteUserDetails(@Min(1) @PathVariable Long userId) {

        userDetailsService.deleteUserDetails(userId);
    }

    @RequestMapping(value = "/image", method=RequestMethod.PUT)
    public void uploadImage(@PathVariable Long userId, @RequestParam("file") MultipartFile image) throws IOException {

        userDetailsService.saveImage(userId, image);
    }
}