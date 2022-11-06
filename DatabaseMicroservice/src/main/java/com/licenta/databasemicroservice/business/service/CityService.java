package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.persistence.entity.City;
import com.licenta.databasemicroservice.persistence.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    static final String CITY_NOT_FOUND_MESSAGE = "City with id <%s> does not exist.";

    public City getCityOrElseThrowException(Integer cityId) {

        return cityRepository.findById(cityId).orElseThrow(
                () -> new NotFoundException(String.format(CITY_NOT_FOUND_MESSAGE, cityId))
        );
    }
}
