package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.ExperienceLevelDTO;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;

public interface IExperienceLevelService {

    ExperienceLevel getExperienceLevelOrElseThrowException(Integer experienceLevelId);
    Iterable<ExperienceLevelDTO> getExperienceLevels();

    ExperienceLevelDTO getExperienceLevel(Integer experienceLevelId);
}
