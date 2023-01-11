package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ILanguageLevelService;
import com.licenta.databasemicroservice.business.model.LanguageLevelDTO;
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
@RequestMapping(value="/language-levels")
public class LanguageLevelController {

    @Autowired
    private ILanguageLevelService languageLevelService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<LanguageLevelDTO> getAll() {

        return languageLevelService.getAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public LanguageLevelDTO getById(@Min(1) @PathVariable Integer id) {

        return languageLevelService.getById(id);
    }
}
