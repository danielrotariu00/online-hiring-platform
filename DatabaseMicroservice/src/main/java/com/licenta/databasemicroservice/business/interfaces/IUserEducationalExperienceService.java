package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserEducationalExperienceDTO;

public interface IUserEducationalExperienceService {
    UserEducationalExperienceDTO add(Long userId, UserEducationalExperienceDTO userEducationalExperienceDTO);
    void delete(Long userId, Long userEducationalExperienceId);
    Iterable<UserEducationalExperienceDTO> getByUserId(Long userId);
}
