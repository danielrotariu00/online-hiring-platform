package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.company.CompanyDTO;
import com.licenta.databasemicroservice.persistence.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CompanyMapper {

    Company toModel(CompanyDTO request);

    @Mapping(target="cityId", source="city.id")
    CompanyDTO toResponse(Company company);
}
