package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.persistence.entity.Country;
import com.licenta.databasemicroservice.persistence.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    static final String COUNTRY_NOT_FOUND_MESSAGE = "Country with id <%s> does not exist.";

    public Country getCountryOrElseThrowException(Integer countryId) {

        return countryRepository.findById(countryId).orElseThrow(
                () -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE, countryId))
        );
    }
}
