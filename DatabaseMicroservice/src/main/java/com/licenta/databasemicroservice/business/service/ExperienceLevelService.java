package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IExperienceLevelService;
import com.licenta.databasemicroservice.business.model.experiencelevel.ExperienceLevelResponse;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.ExperienceLevelMapper;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;
import com.licenta.databasemicroservice.persistence.repository.ExperienceLevelRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ExperienceLevelService implements IExperienceLevelService {

    @Autowired
    private ExperienceLevelRepository experienceLevelRepository;

    private final ExperienceLevelMapper experienceLevelMapper = Mappers.getMapper(ExperienceLevelMapper.class);

    static final String EXPERIENCE_LEVEL_NOT_FOUND_MESSAGE = "ExperienceLevel with id <%s> does not exist.";

    @Override
    public ExperienceLevel getExperienceLevelOrElseThrowException(Integer experienceLevelId) {

        return experienceLevelRepository.findById(experienceLevelId).orElseThrow(
                () -> new NotFoundException(String.format(EXPERIENCE_LEVEL_NOT_FOUND_MESSAGE, experienceLevelId))
        );
    }

    @Override
    public Iterable<ExperienceLevelResponse> getExperienceLevels() {
        return experienceLevelRepository.findAll().stream()
                .map(experienceLevelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExperienceLevelResponse getExperienceLevel(Integer experienceLevelId) {
        ExperienceLevel experienceLevel = getExperienceLevelOrElseThrowException(experienceLevelId);

        return experienceLevelMapper.toResponse(experienceLevel);
    }
}
