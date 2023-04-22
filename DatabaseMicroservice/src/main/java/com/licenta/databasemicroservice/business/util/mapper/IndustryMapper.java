package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.IndustryDTO;
import com.licenta.databasemicroservice.persistence.entity.Industry;
import org.mapstruct.Mapper;

@Mapper
public interface IndustryMapper {

    IndustryDTO toResponse(Industry industry);
}
