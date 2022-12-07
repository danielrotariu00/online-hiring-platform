package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.country.CountryResponse;
import com.licenta.databasemicroservice.persistence.entity.Country;
import org.mapstruct.Mapper;

@Mapper
public interface CountryMapper {

    CountryResponse toResponse(Country country);
}
