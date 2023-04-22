package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IUserProfessionalExperienceService;
import com.licenta.databasemicroservice.business.model.UserProfessionalExperienceDTO;
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
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/api/users/{userId}/professional-experience")
public class UserProfessionalExperienceController {

    @Autowired
    private IUserProfessionalExperienceService userProfessionalExperienceService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    public UserProfessionalExperienceDTO add(@Min(1) @PathVariable Long userId,
                                             @Valid @RequestBody UserProfessionalExperienceDTO userProfessionalExperienceDTO) {

        return userProfessionalExperienceService.add(userId, userProfessionalExperienceDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{professionalExperienceId}", method=RequestMethod.PUT)
    public UserProfessionalExperienceDTO update(@Min(1) @PathVariable Long userId,
                                                @PathVariable Long professionalExperienceId,
                                                @Valid @RequestBody UserProfessionalExperienceDTO userProfessionalExperienceDTO) {

        return userProfessionalExperienceService.update(userId, professionalExperienceId, userProfessionalExperienceDTO);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<UserProfessionalExperienceDTO> getByUserId(@Min(1) @PathVariable Long userId) {

        return userProfessionalExperienceService.getByUserId(userId);
    }

    @RequestMapping(value="/{professionalExperienceId}", method=RequestMethod.DELETE)
    public void delete(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long professionalExperienceId) {

        userProfessionalExperienceService.delete(userId, professionalExperienceId);
    }
}