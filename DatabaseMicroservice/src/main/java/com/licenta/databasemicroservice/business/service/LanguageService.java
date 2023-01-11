package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ILanguageService;
import com.licenta.databasemicroservice.business.model.LanguageDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.LanguageMapper;
import com.licenta.databasemicroservice.persistence.entity.Language;
import com.licenta.databasemicroservice.persistence.repository.LanguageRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LanguageService implements ILanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    private final LanguageMapper languageMapper = Mappers.getMapper(LanguageMapper.class);

    static final String LANGUAGE_NOT_FOUND_MESSAGE = "Language with id <%s> does not exist.";

    @Override
    public LanguageDTO getById(Integer id) {
        Language language = getOrElseThrowException(id);

        return languageMapper.toDTO(language);
    }

    @Override
    public Language getOrElseThrowException(Integer id) {

        return languageRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(LANGUAGE_NOT_FOUND_MESSAGE, id))
        );
    }

    @Override
    public Iterable<LanguageDTO> getAll() {

        return languageRepository.findAll().stream()
                .map(languageMapper::toDTO)
                .collect(Collectors.toList());
    }
}
