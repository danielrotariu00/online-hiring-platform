package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.experiencelevel.ExperienceLevelResponse;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;

public interface IExperienceLevelService {

    ExperienceLevel getExperienceLevelOrElseThrowException(Integer experienceLevelId);
    Iterable<ExperienceLevelResponse> getExperienceLevels();
}
