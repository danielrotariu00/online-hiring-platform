package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.SkillDTO;
import com.licenta.databasemicroservice.persistence.entity.Skill;
import org.mapstruct.Mapper;

@Mapper
public interface SkillMapper {
    SkillDTO toDTO(Skill language);
}
