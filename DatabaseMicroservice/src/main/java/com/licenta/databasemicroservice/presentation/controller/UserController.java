package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IUserService;
import com.licenta.databasemicroservice.business.model.user.AuthenticateUserRequest;
import com.licenta.databasemicroservice.business.model.user.CreateUserRequest;
import com.licenta.databasemicroservice.business.model.user.UpdateUserPasswordRequest;
import com.licenta.databasemicroservice.business.model.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    public void createUser (@Valid @RequestBody CreateUserRequest createUserRequest) {

        userService.createUser(createUserRequest);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<UserResponse> getAllUsers() {

        return userService.getUsers();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public UserResponse getUser(@Min(1) @PathVariable Long id) {

        return userService.getUser(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PATCH)
    public void updateUserPassword(@Min(1) @PathVariable Long id, @Valid @RequestBody UpdateUserPasswordRequest request) {

        userService.updateUserPassword(id, request);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void deleteUser(@Min(1) @PathVariable Long id) {

        userService.deleteUser(id);
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public void login(@Valid @RequestBody AuthenticateUserRequest request) {

        userService.authenticate(request);
    }
}