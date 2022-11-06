package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.company.CreateCompanyRequest;
import com.licenta.databasemicroservice.persistence.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CompanyMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    Company toModel(CreateCompanyRequest request);

    CompanyResponse toResponse(Company company);
}
