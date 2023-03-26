package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.CompanyRecruiterDTO;
import com.licenta.databasemicroservice.persistence.entity.CompanyRecruiter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CompanyRecruiterMapper {

    @Mapping(target="companyId", source="company.id")
    CompanyRecruiterDTO toDTO(CompanyRecruiter companyRecruiter);
}
