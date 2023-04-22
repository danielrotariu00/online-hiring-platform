package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.CompanyManagerDTO;
import com.licenta.databasemicroservice.persistence.entity.CompanyManager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CompanyManagerMapper {

    @Mapping(target="companyId", source="company.id")
    CompanyManagerDTO toDTO(CompanyManager companyManager);
}
