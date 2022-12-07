package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.experiencelevel.ExperienceLevelResponse;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;
import org.mapstruct.Mapper;

@Mapper
public interface ExperienceLevelMapper {

    ExperienceLevelResponse toResponse(ExperienceLevel experienceLevel);
}
