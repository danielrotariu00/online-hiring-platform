package com.licenta.database.business.util.mappers;

import com.licenta.database.business.model.company.CompanyResponse;
import com.licenta.database.business.model.company.CreateCompanyRequest;
import com.licenta.database.persistence.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CompanyMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    Company toModel(CreateCompanyRequest request);

    CompanyResponse toResponse(Company company);
}
