package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.CityDTO;
import com.licenta.databasemicroservice.persistence.entity.City;

public interface ICityService {

    City getCityOrElseThrowException(Integer cityId);

    Iterable<CityDTO> getCities();

    Iterable<CityDTO> getCitiesByCountry(Integer countryId);

    CityDTO getCity(Integer cityId);
}
