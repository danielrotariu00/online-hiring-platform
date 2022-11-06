package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;
import com.licenta.databasemicroservice.persistence.repository.ExperienceLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceLevelService {

    @Autowired
    private ExperienceLevelRepository experienceLevelRepository;

    static final String EXPERIENCE_LEVEL_NOT_FOUND_MESSAGE = "ExperienceLevel with id <%s> does not exist.";

    public ExperienceLevel getExperienceLevelOrElseThrowException(Integer experienceLevelId) {

        return experienceLevelRepository.findById(experienceLevelId).orElseThrow(
                () -> new NotFoundException(String.format(EXPERIENCE_LEVEL_NOT_FOUND_MESSAGE, experienceLevelId))
        );
    }
}
