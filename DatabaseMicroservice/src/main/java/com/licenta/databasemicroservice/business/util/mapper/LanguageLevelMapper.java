package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.LanguageLevelDTO;
import com.licenta.databasemicroservice.persistence.entity.LanguageLevel;
import org.mapstruct.Mapper;

@Mapper
public interface LanguageLevelMapper {
    LanguageLevelDTO toDTO(LanguageLevel language);
}
