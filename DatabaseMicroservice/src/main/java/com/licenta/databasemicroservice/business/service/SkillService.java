package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ISkillService;
import com.licenta.databasemicroservice.business.model.SkillDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.SkillMapper;
import com.licenta.databasemicroservice.persistence.entity.Skill;
import com.licenta.databasemicroservice.persistence.repository.SkillRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SkillService implements ISkillService {

    @Autowired
    private SkillRepository skillRepository;

    private final SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);

    static final String SKILL_NOT_FOUND_MESSAGE = "Skill with id <%s> does not exist.";

    @Override
    public SkillDTO getById(Integer id) {
        Skill skill = getOrElseThrowException(id);

        return skillMapper.toDTO(skill);
    }

    @Override
    public Skill getOrElseThrowException(Integer id) {

        return skillRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(SKILL_NOT_FOUND_MESSAGE, id))
        );
    }

    @Override
    public Iterable<SkillDTO> getAll() {

        return skillRepository.findAll().stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toList());
    }
}
