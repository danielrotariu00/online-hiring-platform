package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ILanguageLevelService;
import com.licenta.databasemicroservice.business.model.LanguageLevelDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.LanguageLevelMapper;
import com.licenta.databasemicroservice.persistence.entity.LanguageLevel;
import com.licenta.databasemicroservice.persistence.repository.LanguageLevelRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LanguageLevelService implements ILanguageLevelService {

    @Autowired
    private LanguageLevelRepository languageLevelRepository;

    private final LanguageLevelMapper languageLevelMapper = Mappers.getMapper(LanguageLevelMapper.class);

    static final String LANGUAGE_LEVEL_NOT_FOUND_MESSAGE = "Language Level with id <%s> does not exist.";

    @Override
    public LanguageLevelDTO getById(Integer id) {
        LanguageLevel languageLevel = getOrElseThrowException(id);

        return languageLevelMapper.toDTO(languageLevel);
    }

    @Override
    public LanguageLevel getOrElseThrowException(Integer id) {

        return languageLevelRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(LANGUAGE_LEVEL_NOT_FOUND_MESSAGE, id))
        );
    }

    @Override
    public Iterable<LanguageLevelDTO> getAll() {

        return languageLevelRepository.findAll().stream()
                .map(languageLevelMapper::toDTO)
                .collect(Collectors.toList());
    }
}
