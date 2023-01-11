package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.EducationalInstitutionDTO;
import com.licenta.databasemicroservice.persistence.entity.EducationalInstitution;

public interface IEducationalInstitutionService {
    EducationalInstitutionDTO getById(Long id);

    EducationalInstitution getOrElseThrowException(Long id);

    Iterable<EducationalInstitutionDTO> getAll();
}
