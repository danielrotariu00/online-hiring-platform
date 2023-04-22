package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.CountryDTO;
import com.licenta.databasemicroservice.persistence.entity.Country;

public interface ICountryService {

    Country getCountryOrElseThrowException(Integer countryId);

    Iterable<CountryDTO> getCountries();

    CountryDTO getCountry(Integer countryId);
}
