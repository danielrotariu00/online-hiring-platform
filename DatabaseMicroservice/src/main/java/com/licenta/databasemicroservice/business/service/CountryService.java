package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICountryService;
import com.licenta.databasemicroservice.business.model.country.CountryResponse;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.CountryMapper;
import com.licenta.databasemicroservice.persistence.entity.Country;
import com.licenta.databasemicroservice.persistence.repository.CountryRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CountryService implements ICountryService {

    @Autowired
    private CountryRepository countryRepository;

    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);

    static final String COUNTRY_NOT_FOUND_MESSAGE = "Country with id <%s> does not exist.";

    @Override
    public Country getCountryOrElseThrowException(Integer countryId) {

        return countryRepository.findById(countryId).orElseThrow(
                () -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE, countryId))
        );
    }

    @Override
    public Iterable<CountryResponse> getCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false)
                .map(countryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CountryResponse getCountry(Integer countryId) {
        Country country = getCountryOrElseThrowException(countryId);

        return countryMapper.toResponse(country);
    }
}
