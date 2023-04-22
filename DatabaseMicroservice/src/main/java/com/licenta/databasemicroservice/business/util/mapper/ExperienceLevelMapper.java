package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.ExperienceLevelDTO;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;
import org.mapstruct.Mapper;

@Mapper
public interface ExperienceLevelMapper {

    ExperienceLevelDTO toResponse(ExperienceLevel experienceLevel);
}
