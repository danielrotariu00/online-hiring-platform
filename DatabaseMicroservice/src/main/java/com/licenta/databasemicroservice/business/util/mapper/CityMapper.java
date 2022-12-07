package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.city.CityResponse;
import com.licenta.databasemicroservice.persistence.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CityMapper {

    @Mapping(target="countryId", source="country.id")
    CityResponse toResponse(City city);
}
