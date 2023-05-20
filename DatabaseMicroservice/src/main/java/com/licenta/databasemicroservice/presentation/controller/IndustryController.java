package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IIndustryService;
import com.licenta.databasemicroservice.business.model.IndustryDTO;
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
@RequestMapping(value="/api/industries")
public class IndustryController {

    @Autowired
    private IIndustryService industryService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<IndustryDTO> getAllIndustries() {

        return industryService.getIndustries();
    }

    @RequestMapping(value="/{industryId}", method=RequestMethod.GET)
    public IndustryDTO getIndustry(@Min(1) @PathVariable Integer industryId) {

        return industryService.getIndustry(industryId);
    }
}
