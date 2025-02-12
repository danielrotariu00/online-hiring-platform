package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IUserEducationalExperienceService;
import com.licenta.databasemicroservice.business.model.UserEducationalExperienceDTO;
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
@RequestMapping(value="/api/users/{userId}/educational-experience")
public class UserEducationalExperienceController {

    @Autowired
    private IUserEducationalExperienceService userEducationalExperienceService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    public UserEducationalExperienceDTO add(@Min(1) @PathVariable Long userId,
                                            @Valid @RequestBody UserEducationalExperienceDTO userEducationalExperienceDTO) {

        return userEducationalExperienceService.add(userId, userEducationalExperienceDTO);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<UserEducationalExperienceDTO> getByUserId(@Min(1) @PathVariable Long userId) {

        return userEducationalExperienceService.getByUserId(userId);
    }

    @RequestMapping(value="/{educationalExperienceId}", method=RequestMethod.DELETE)
    public void delete(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long educationalExperienceId) {

        userEducationalExperienceService.delete(userId, educationalExperienceId);
    }
}