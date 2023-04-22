package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.CityDTO;
import com.licenta.databasemicroservice.persistence.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CityMapper {

    @Mapping(target="countryId", source="country.id")
    CityDTO toResponse(City city);
}
