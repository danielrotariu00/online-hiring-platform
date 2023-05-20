package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICityService;
import com.licenta.databasemicroservice.business.model.CityDTO;
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
@RequestMapping(value="/api")
public class CityController {

    @Autowired
    private ICityService cityService;

    @RequestMapping(value="/cities", method= RequestMethod.GET)
    public Iterable<CityDTO> getAllCities() {

        return cityService.getCities();
    }

    @RequestMapping(value="/cities/{cityId}", method=RequestMethod.GET)
    public CityDTO getCity(@Min(1) @PathVariable Integer cityId) {

        return cityService.getCity(cityId);
    }

    @RequestMapping(value="/countries/{countryId}/cities", method= RequestMethod.GET)
    public Iterable<CityDTO> getCitiesByCountry(@Min(1) @PathVariable Integer countryId) {

        return cityService.getCitiesByCountry(countryId);
    }
}
