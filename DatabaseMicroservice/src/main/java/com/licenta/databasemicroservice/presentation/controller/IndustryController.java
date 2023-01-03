package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IIndustryService;
import com.licenta.databasemicroservice.business.model.industry.IndustryResponse;
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
@RequestMapping(value="/industries")
public class IndustryController {

    @Autowired
    private IIndustryService industryService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<IndustryResponse> getAllIndustries() {

        return industryService.getIndustries();
    }

    @RequestMapping(value="/{industryId}", method=RequestMethod.GET)
    public IndustryResponse getIndustry(@Min(1) @PathVariable Integer industryId) {

        return industryService.getIndustry(industryId);
    }
}
