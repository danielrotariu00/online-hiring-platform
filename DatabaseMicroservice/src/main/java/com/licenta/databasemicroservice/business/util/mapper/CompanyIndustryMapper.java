package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryResponse;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CompanyIndustryMapper {

    @Mapping(target="companyId", source="company.id")
    @Mapping(target="industryId", source="industry.id")
    CompanyIndustryResponse toResponse(CompanyIndustry companyIndustry);
}