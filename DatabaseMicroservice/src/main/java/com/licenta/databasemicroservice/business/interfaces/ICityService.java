package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.city.CityResponse;
import com.licenta.databasemicroservice.persistence.entity.City;

public interface ICityService {

    City getCityOrElseThrowException(Integer cityId);

    Iterable<CityResponse> getCities();

    Iterable<CityResponse> getCitiesByCountry(Integer countryId);

    CityResponse getCity(Integer cityId);
}
