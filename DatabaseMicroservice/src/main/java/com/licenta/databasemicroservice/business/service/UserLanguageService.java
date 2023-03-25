package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ILanguageLevelService;
import com.licenta.databasemicroservice.business.interfaces.ILanguageService;
import com.licenta.databasemicroservice.business.interfaces.IUserLanguageService;
import com.licenta.databasemicroservice.business.interfaces.IUserService;
import com.licenta.databasemicroservice.business.model.UserLanguageDTO;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.UserLanguageMapper;
import com.licenta.databasemicroservice.persistence.entity.Language;
import com.licenta.databasemicroservice.persistence.entity.LanguageLevel;
import com.licenta.databasemicroservice.persistence.entity.User;
import com.licenta.databasemicroservice.persistence.entity.UserLanguage;
import com.licenta.databasemicroservice.persistence.repository.UserLanguageRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLanguageService implements IUserLanguageService {
    @Autowired
    private IUserService userService;
    @Autowired
    private ILanguageService languageService;
    @Autowired
    private ILanguageLevelService languageLevelService;

    @Autowired
    private UserLanguageRepository userLanguageRepository;

    private final UserLanguageMapper userLanguageMapper = Mappers.getMapper(UserLanguageMapper.class);

    private static final String USER_LANGUAGE_NOT_FOUND_MESSAGE = "Language with id <%d> does not exist for user with id <%d>.";
    private static final String USER_LANGUAGE_ALREADY_EXISTS_MESSAGE = "Language with id <%d> already exists for user with id <%d>.";

    @Override
    public UserLanguageDTO add(Long userId, UserLanguageDTO userLanguageDTO) {
        Integer languageId = userLanguageDTO.getLanguageId();
        Integer languageLevelId = userLanguageDTO.getLanguageLevelId();

        User user = userService.getUserOrElseThrowException(userId);
        Language language = languageService.getOrElseThrowException(languageId);
        LanguageLevel languageLevel = languageLevelService.getOrElseThrowException(languageLevelId);

        Optional<UserLanguage> userLanguage =
                userLanguageRepository.findByUserIdAndLanguageId(userId, languageId);

        if (userLanguage.isPresent()) {
            throw new AlreadyExistsException(String.format(USER_LANGUAGE_ALREADY_EXISTS_MESSAGE, languageId, userId));
        }

        UserLanguage newUserLanguage = UserLanguage.builder()
                .user(user)
                .language(language)
                .languageLevel(languageLevel)
                .build();

        return userLanguageMapper.toDTO(
                userLanguageRepository.save(newUserLanguage)
        );
    }

    @Override
    public Iterable<UserLanguageDTO> getByUserId(Long userId) {
        userService.getUserOrElseThrowException(userId);

        return userLanguageRepository.findAllByUserId(userId).stream()
                .map(userLanguageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, Integer languageId) {
        userService.getUserOrElseThrowException(userId);
        languageService.getOrElseThrowException(languageId);

        Optional<UserLanguage> userLanguage = userLanguageRepository.findByUserIdAndLanguageId(userId, languageId);

        userLanguageRepository.delete(userLanguage.orElseThrow(
                () -> new NotFoundException(String.format(USER_LANGUAGE_NOT_FOUND_MESSAGE, languageId, userId)))
        );
    }
}
