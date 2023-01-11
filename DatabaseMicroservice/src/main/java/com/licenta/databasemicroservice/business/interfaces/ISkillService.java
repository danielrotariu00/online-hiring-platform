package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.SkillDTO;
import com.licenta.databasemicroservice.persistence.entity.Skill;

public interface ISkillService {
    SkillDTO getById(Integer id);

    Skill getOrElseThrowException(Integer id);

    Iterable<SkillDTO> getAll();
}
