package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserProfessionalExperienceDTO;

public interface IUserProfessionalExperienceService {
    UserProfessionalExperienceDTO add(UserProfessionalExperienceDTO userProfessionalExperienceDTO);
    void delete(Long userProfessionalExperienceId);
    Iterable<UserProfessionalExperienceDTO> getByUserId(Long userId);
}
