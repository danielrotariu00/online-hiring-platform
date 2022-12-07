package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.industry.IndustryResponse;
import com.licenta.databasemicroservice.persistence.entity.Industry;
import org.mapstruct.Mapper;

@Mapper
public interface IndustryMapper {

    IndustryResponse toResponse(Industry industry);
}
