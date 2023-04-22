package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IExperienceLevelService;
import com.licenta.databasemicroservice.business.model.ExperienceLevelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/api/experience-levels")
public class ExperienceLevelController {

    @Autowired
    private IExperienceLevelService experienceLevelService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<ExperienceLevelDTO> getAllExperienceLevels() {

        return experienceLevelService.getExperienceLevels();
    }

    @RequestMapping(value="/{experienceLevelId}", method=RequestMethod.GET)
    public ExperienceLevelDTO getJExperienceLevel(@Min(1) @PathVariable Integer experienceLevelId) {

        return experienceLevelService.getExperienceLevel(experienceLevelId);
    }
}
