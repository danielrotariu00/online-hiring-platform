package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICityService;
import com.licenta.databasemicroservice.business.model.city.CityResponse;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.CityMapper;
import com.licenta.databasemicroservice.persistence.entity.City;
import com.licenta.databasemicroservice.persistence.repository.CityRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CityService implements ICityService {

    @Autowired
    private CityRepository cityRepository;

    private final CityMapper cityMapper = Mappers.getMapper(CityMapper.class);
    static final String CITY_NOT_FOUND_MESSAGE = "City with id <%d> does not exist.";

    @Override
    public City getCityOrElseThrowException(Integer cityId) {

        return cityRepository.findById(cityId).orElseThrow(
                () -> new NotFoundException(String.format(CITY_NOT_FOUND_MESSAGE, cityId))
        );
    }

    @Override
    public Iterable<CityResponse> getCities() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(), false)
                .map(cityMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<CityResponse> getCitiesByCountry(Integer countryId) {
        return StreamSupport.stream(cityRepository.findCitiesByCountryId(countryId).spliterator(), false)
                .map(cityMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CityResponse getCity(Integer cityId) {
        City city = getCityOrElseThrowException(cityId);

        return cityMapper.toResponse(city);
    }
}
