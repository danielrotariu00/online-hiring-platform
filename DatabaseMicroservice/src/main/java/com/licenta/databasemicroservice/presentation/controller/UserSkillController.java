package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IUserSkillService;
import com.licenta.databasemicroservice.business.model.UserSkillDTO;
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
@RequestMapping(value="/api/users/{userId}/skills")
public class UserSkillController {

    @Autowired
    private IUserSkillService userSkillService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method=RequestMethod.POST)
    public UserSkillDTO add(@Min(1) @PathVariable Long userId, @Valid @RequestBody UserSkillDTO userSkillDTO) {

        return userSkillService.add(userId, userSkillDTO);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<UserSkillDTO> getByUserId(@Min(1) @PathVariable Long userId) {

        return userSkillService.getByUserId(userId);
    }

    @RequestMapping(value="/{skillId}", method=RequestMethod.DELETE)
    public void delete(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Integer skillId) {

        userSkillService.delete(userId, skillId);
    }
}