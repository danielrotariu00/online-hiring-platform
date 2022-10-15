package com.licenta.database.business.services;

import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.persistence.models.City;
import com.licenta.database.persistence.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    static final String CITY_NOT_FOUND_MESSAGE = "City <%s> does not exist.";

    public City getCityOrElseThrowException(String cityName) {

        return cityRepository.findById(cityName).orElseThrow(
                () -> new NotFoundException(String.format(CITY_NOT_FOUND_MESSAGE, cityName))
        );
    }
}
