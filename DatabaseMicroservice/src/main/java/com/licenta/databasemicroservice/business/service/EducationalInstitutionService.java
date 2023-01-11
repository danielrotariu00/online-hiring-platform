package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IEducationalInstitutionService;
import com.licenta.databasemicroservice.business.model.EducationalInstitutionDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.EducationalInstitutionMapper;
import com.licenta.databasemicroservice.persistence.entity.EducationalInstitution;
import com.licenta.databasemicroservice.persistence.repository.EducationalInstitutionRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EducationalInstitutionService implements IEducationalInstitutionService {

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    private final EducationalInstitutionMapper industryMapper = Mappers.getMapper(EducationalInstitutionMapper.class);

    static final String INSTITUTION_NOT_FOUND_MESSAGE = "Educational Institution with id <%s> does not exist.";

    @Override
    public EducationalInstitutionDTO getById(Long id) {
        EducationalInstitution educationalInstitution = getOrElseThrowException(id);

        return industryMapper.toDTO(educationalInstitution);
    }

    @Override
    public EducationalInstitution getOrElseThrowException(Long id) {

        return educationalInstitutionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(INSTITUTION_NOT_FOUND_MESSAGE, id))
        );
    }

    @Override
    public Iterable<EducationalInstitutionDTO> getAll() {

        return educationalInstitutionRepository.findAll().stream()
                .map(industryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
