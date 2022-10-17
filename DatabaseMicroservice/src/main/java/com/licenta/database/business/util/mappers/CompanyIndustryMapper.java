package com.licenta.database.business.util.mappers;

import com.licenta.database.business.model.companyindustry.CompanyIndustryResponse;
import com.licenta.database.persistence.entities.CompanyIndustry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CompanyIndustryMapper {

    @Mapping(target="companyId", source="company.id")
    @Mapping(target="industryId", source="industry.id")
    CompanyIndustryResponse toResponse(CompanyIndustry companyIndustry);
}
