package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserEducationalExperienceDTO;

public interface IUserEducationalExperienceService {
    void add(UserEducationalExperienceDTO userEducationalExperienceDTO);
    void delete(Long userEducationalExperienceId);
    Iterable<UserEducationalExperienceDTO> getByUserId(Long userId);
}
