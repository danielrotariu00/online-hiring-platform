package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserProfessionalExperienceDTO;

public interface IUserProfessionalExperienceService {
    UserProfessionalExperienceDTO add(UserProfessionalExperienceDTO userProfessionalExperienceDTO);
    void delete(Long userProfessionalExperienceId);
    UserProfessionalExperienceDTO update(Long id, UserProfessionalExperienceDTO userProfessionalExperienceDTO);
    Iterable<UserProfessionalExperienceDTO> getByUserId(Long userId);
}
