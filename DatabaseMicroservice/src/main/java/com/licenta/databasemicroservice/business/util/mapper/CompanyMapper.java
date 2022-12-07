package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.company.CreateCompanyRequest;
import com.licenta.databasemicroservice.persistence.entity.Company;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CompanyMapper {

    Company toModel(CreateCompanyRequest request);
    CompanyResponse toResponse(Company company);
}
