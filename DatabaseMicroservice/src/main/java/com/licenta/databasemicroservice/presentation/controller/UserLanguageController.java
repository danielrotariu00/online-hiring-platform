package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IUserLanguageService;
import com.licenta.databasemicroservice.business.model.UserLanguageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin
@RequestMapping(value="/api/users/{userId}/languages")
public class UserLanguageController {

    @Autowired
    private IUserLanguageService userLanguageService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    public UserLanguageDTO add(@Min(1) @PathVariable Long userId, @Valid @RequestBody UserLanguageDTO userLanguageDTO) {

        return userLanguageService.add(userId, userLanguageDTO);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<UserLanguageDTO> getByUserId(@Min(1) @PathVariable Long userId) {

        return userLanguageService.getByUserId(userId);
    }

    @RequestMapping(value="/{languageId}", method=RequestMethod.DELETE)
    public void delete(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Integer languageId) {

        userLanguageService.delete(userId, languageId);
    }
}