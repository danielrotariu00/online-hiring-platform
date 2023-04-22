package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.UserProfessionalExperienceDTO;
import com.licenta.databasemicroservice.persistence.entity.UserProfessionalExperience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserProfessionalExperienceMapper {

    @Mapping(target="companyId", source="company.id")
    UserProfessionalExperienceDTO toDTO(UserProfessionalExperience userProfessionalExperience);
}
