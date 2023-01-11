package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ISkillService;
import com.licenta.databasemicroservice.business.model.SkillDTO;
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
@RequestMapping(value="/skills")
public class SkillController {

    @Autowired
    private ISkillService skillService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<SkillDTO> getAll() {

        return skillService.getAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public SkillDTO getById(@Min(1) @PathVariable Integer id) {

        return skillService.getById(id);
    }
}
