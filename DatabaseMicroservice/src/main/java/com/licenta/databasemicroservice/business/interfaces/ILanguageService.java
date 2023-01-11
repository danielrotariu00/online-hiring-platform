package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.LanguageDTO;
import com.licenta.databasemicroservice.persistence.entity.Language;

public interface ILanguageService {
    LanguageDTO getById(Integer id);

    Language getOrElseThrowException(Integer id);

    Iterable<LanguageDTO> getAll();
}
