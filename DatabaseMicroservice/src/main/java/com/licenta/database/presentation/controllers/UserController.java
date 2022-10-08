package com.licenta.database.presentation.controllers;

import com.licenta.database.business.interfaces.IUserService;
import com.licenta.database.business.models.user.AuthenticateUserRequest;
import com.licenta.database.business.models.user.CreateUserRequest;
import com.licenta.database.business.models.user.UpdateUserPasswordRequest;
import com.licenta.database.business.models.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    public void createUser (@RequestBody CreateUserRequest createUserRequest) {

        userService.createUser(createUserRequest);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<UserResponse> getAllUsers() {

        return userService.getUsers();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public UserResponse getUser(@PathVariable String id) {

        return userService.getUser(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PATCH)
    public void updateUserPassword(@PathVariable String id,
                                   @RequestBody UpdateUserPasswordRequest request) {

        userService.updateUserPassword(id, request);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable String id) {

        userService.deleteUser(id);
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public void login(@RequestBody AuthenticateUserRequest request) {

        userService.authenticate(request);
    }
}