package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.country.CountryResponse;
import com.licenta.databasemicroservice.persistence.entity.Country;

public interface ICountryService {

    Country getCountryOrElseThrowException(Integer countryId);

    Iterable<CountryResponse> getCountries();

    CountryResponse getCountry(Integer countryId);
}
