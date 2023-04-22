package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.CountryDTO;
import com.licenta.databasemicroservice.persistence.entity.Country;
import org.mapstruct.Mapper;

@Mapper
public interface CountryMapper {

    CountryDTO toResponse(Country country);
}
