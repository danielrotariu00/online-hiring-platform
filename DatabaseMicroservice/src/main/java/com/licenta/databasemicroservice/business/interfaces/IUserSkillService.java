package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserSkillDTO;

public interface IUserSkillService {
    void add(Long userId, UserSkillDTO userSkillDTO);

    void delete(Long userId, Integer skillId);

    Iterable<UserSkillDTO> getByUserId(Long userId);
}
