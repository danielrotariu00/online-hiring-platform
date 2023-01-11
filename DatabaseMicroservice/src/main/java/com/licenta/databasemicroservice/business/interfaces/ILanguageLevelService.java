package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.LanguageLevelDTO;
import com.licenta.databasemicroservice.persistence.entity.LanguageLevel;

public interface ILanguageLevelService {
    LanguageLevelDTO getById(Integer id);

    LanguageLevel getOrElseThrowException(Integer id);

    Iterable<LanguageLevelDTO> getAll();
}
