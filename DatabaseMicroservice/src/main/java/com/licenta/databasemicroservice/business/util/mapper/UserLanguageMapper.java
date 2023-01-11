package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.UserLanguageDTO;
import com.licenta.databasemicroservice.persistence.entity.UserLanguage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserLanguageMapper {

    @Mapping(target="languageId", source="language.id")
    @Mapping(target="languageLevelId", source="languageLevel.id")
    UserLanguageDTO toDTO(UserLanguage userLanguage);
}
