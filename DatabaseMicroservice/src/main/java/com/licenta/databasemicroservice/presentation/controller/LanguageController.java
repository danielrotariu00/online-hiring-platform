package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ILanguageService;
import com.licenta.databasemicroservice.business.model.LanguageDTO;
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
@RequestMapping(value="/api/languages")
public class LanguageController {

    @Autowired
    private ILanguageService languageService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<LanguageDTO> getAll() {

        return languageService.getAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public LanguageDTO getById(@Min(1) @PathVariable Integer id) {

        return languageService.getById(id);
    }
}
