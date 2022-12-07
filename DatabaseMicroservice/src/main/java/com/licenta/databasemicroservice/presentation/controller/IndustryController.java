package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IIndustryService;
import com.licenta.databasemicroservice.business.model.industry.IndustryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/industries")
public class IndustryController {

    @Autowired
    private IIndustryService industryService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<IndustryResponse> getAllIndustries() {

        return industryService.getIndustries();
    }
}
