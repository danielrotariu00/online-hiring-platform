package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserProfessionalExperienceDTO;

public interface IUserProfessionalExperienceService {
    UserProfessionalExperienceDTO add(Long userId, UserProfessionalExperienceDTO userProfessionalExperienceDTO);
    void delete(Long userId, Long userProfessionalExperienceId);
    UserProfessionalExperienceDTO update(Long userId, Long id, UserProfessionalExperienceDTO userProfessionalExperienceDTO);
    Iterable<UserProfessionalExperienceDTO> getByUserId(Long userId);
}
