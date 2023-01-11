package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.EducationalInstitutionDTO;
import com.licenta.databasemicroservice.persistence.entity.EducationalInstitution;
import org.mapstruct.Mapper;

@Mapper
public interface EducationalInstitutionMapper {

    EducationalInstitutionDTO toDTO(EducationalInstitution educationalInstitution);
}
