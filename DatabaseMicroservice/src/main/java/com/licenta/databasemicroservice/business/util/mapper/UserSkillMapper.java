package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.UserSkillDTO;
import com.licenta.databasemicroservice.persistence.entity.UserSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserSkillMapper {

    @Mapping(target="skillId", source="skill.id")
    UserSkillDTO toDTO(UserSkill userSkill);
}
