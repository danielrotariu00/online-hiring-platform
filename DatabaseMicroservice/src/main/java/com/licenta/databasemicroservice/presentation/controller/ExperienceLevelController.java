package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IExperienceLevelService;
import com.licenta.databasemicroservice.business.model.experiencelevel.ExperienceLevelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/experience-levels")
public class ExperienceLevelController {

    @Autowired
    private IExperienceLevelService experienceLevelService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<ExperienceLevelResponse> getAllExperienceLevels() {

        return experienceLevelService.getExperienceLevels();
    }
}
