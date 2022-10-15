package com.licenta.database.business.services;

import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.persistence.models.Country;
import com.licenta.database.persistence.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    static final String COUNTRY_NOT_FOUND_MESSAGE = "Country <%s> does not exist.";

    public Country getCountryOrElseThrowException(String countryName) {

        return countryRepository.findById(countryName).orElseThrow(
                () -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE, countryName))
        );
    }
}
