package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserLanguageDTO;

public interface IUserLanguageService {
    UserLanguageDTO add(Long userId, UserLanguageDTO userLanguageDTO);

    void delete(Long userId, Integer languageId);

    Iterable<UserLanguageDTO> getByUserId(Long userId);
}
