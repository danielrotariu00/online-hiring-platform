package com.licenta.database.presentation.controllers;

import com.licenta.database.business.interfaces.IUserService;
import com.licenta.database.business.models.AuthenticateUserRequest;
import com.licenta.database.business.models.CreateUserRequest;
import com.licenta.database.business.models.UpdateUserPasswordRequest;
import com.licenta.database.persistence.models.UserModel;
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
    public void addUser (@RequestBody CreateUserRequest createUserRequest) {
        userService.addUser(createUserRequest);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<UserModel> getAllUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public UserModel getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @RequestMapping(value="/{username}", method=RequestMethod.PATCH)
    public void updateUserPassword(@PathVariable String username, @RequestBody UpdateUserPasswordRequest request) {
        userService.updateUserPassword(username, request);
    }

    @RequestMapping(value="/{username}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public void login(@RequestBody AuthenticateUserRequest request) {
        userService.authenticate(request);
    }
}