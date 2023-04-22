package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IIndustryService;
import com.licenta.databasemicroservice.business.model.IndustryDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.IndustryMapper;
import com.licenta.databasemicroservice.persistence.entity.Industry;
import com.licenta.databasemicroservice.persistence.repository.IndustryRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IndustryService implements IIndustryService {

    @Autowired
    private IndustryRepository industryRepository;

    private final IndustryMapper industryMapper = Mappers.getMapper(IndustryMapper.class);

    static final String INDUSTRY_NOT_FOUND_MESSAGE = "Industry with id <%s> does not exist.";

    @Override
    public IndustryDTO getIndustry(Integer industryId) {
        Industry industry = getIndustryOrElseThrowException(industryId);

        return industryMapper.toResponse(industry);
    }

    @Override
    public Industry getIndustryOrElseThrowException(Integer industryId) {

        return industryRepository.findById(industryId).orElseThrow(
                () -> new NotFoundException(String.format(INDUSTRY_NOT_FOUND_MESSAGE, industryId))
        );
    }

    @Override
    public Iterable<IndustryDTO> getIndustries() {

        return StreamSupport.stream(industryRepository.findAll().spliterator(), false)
                .map(industryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
