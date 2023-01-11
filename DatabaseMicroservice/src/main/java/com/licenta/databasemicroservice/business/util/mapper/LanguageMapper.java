package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.LanguageDTO;
import com.licenta.databasemicroservice.persistence.entity.Language;
import org.mapstruct.Mapper;

@Mapper
public interface LanguageMapper {
    LanguageDTO toDTO(Language language);
}
