package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.UserEducationalExperienceDTO;
import com.licenta.databasemicroservice.persistence.entity.UserEducationalExperience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserEducationalExperienceMapper {

    @Mapping(target="userId", source="user.id")
    @Mapping(target="educationalInstitutionId", source="educationalInstitution.id")
    UserEducationalExperienceDTO toDTO(UserEducationalExperience userEducationalExperience);
}
