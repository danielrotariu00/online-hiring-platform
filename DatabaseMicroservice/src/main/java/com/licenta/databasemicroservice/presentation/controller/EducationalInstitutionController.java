package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IEducationalInstitutionService;
import com.licenta.databasemicroservice.business.model.EducationalInstitutionDTO;
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
@CrossOrigin
@RequestMapping(value="/api/educational-institutions")
public class EducationalInstitutionController {

    @Autowired
    private IEducationalInstitutionService educationalInstitutionService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<EducationalInstitutionDTO> getAll() {

        return educationalInstitutionService.getAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public EducationalInstitutionDTO getById(@Min(1) @PathVariable Long id) {

        return educationalInstitutionService.getById(id);
    }
}
