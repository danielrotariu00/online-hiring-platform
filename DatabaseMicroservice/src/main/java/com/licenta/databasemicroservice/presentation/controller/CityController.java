package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.ICityService;
import com.licenta.databasemicroservice.business.model.city.CityResponse;
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
public class CityController {

    @Autowired
    private ICityService cityService;

    @RequestMapping(value="/cities", method= RequestMethod.GET)
    public Iterable<CityResponse> getAllCities() {

        return cityService.getCities();
    }

    @RequestMapping(value="/cities/{cityId}", method=RequestMethod.GET)
    public CityResponse getCity(@Min(1) @PathVariable Integer cityId) {

        return cityService.getCity(cityId);
    }

    @RequestMapping(value="/countries/{countryId}/cities", method= RequestMethod.GET)
    public Iterable<CityResponse> getCitiesByCountry(@Min(1) @PathVariable Integer countryId) {

        return cityService.getCitiesByCountry(countryId);
    }
}
