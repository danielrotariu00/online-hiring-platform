package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ISkillService;
import com.licenta.databasemicroservice.business.interfaces.IUserSkillService;
import com.licenta.databasemicroservice.business.model.UserSkillDTO;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.UserSkillMapper;
import com.licenta.databasemicroservice.persistence.entity.Skill;
import com.licenta.databasemicroservice.persistence.entity.UserSkill;
import com.licenta.databasemicroservice.persistence.repository.UserSkillRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserSkillService implements IUserSkillService {
    @Autowired
    private ISkillService skillService;

    @Autowired
    private UserSkillRepository userSkillRepository;

    private final UserSkillMapper userSkillMapper = Mappers.getMapper(UserSkillMapper.class);

    private static final String USER_SKILL_NOT_FOUND_MESSAGE = "Skill with id <%d> does not exist for user with id <%d>.";
    private static final String USER_SKILL_ALREADY_EXISTS_MESSAGE = "Skill with id <%d> already exists for user with id <%d>.";

    @Override
    public UserSkillDTO add(Long userId, UserSkillDTO userSkillDTO) {
        Integer skillId = userSkillDTO.getSkillId();

        Skill skill = skillService.getOrElseThrowException(skillId);

        Optional<UserSkill> userSkill =
                userSkillRepository.findByUserIdAndSkillId(userId, skillId);

        if (userSkill.isPresent()) {
            throw new AlreadyExistsException(String.format(USER_SKILL_ALREADY_EXISTS_MESSAGE, skillId, userId));
        }

        UserSkill newUserSkill = UserSkill.builder()
                .userId(userId)
                .skill(skill)
                .build();

        return userSkillMapper.toDTO(
                userSkillRepository.save(newUserSkill)
        );
    }

    @Override
    public Iterable<UserSkillDTO> getByUserId(Long userId) {
        return userSkillRepository.findAllByUserId(userId).stream()
                .map(userSkillMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, Integer skillId) {
        skillService.getOrElseThrowException(skillId);

        Optional<UserSkill> userSkill = userSkillRepository.findByUserIdAndSkillId(userId, skillId);

        userSkillRepository.delete(userSkill.orElseThrow(
                () -> new NotFoundException(String.format(USER_SKILL_NOT_FOUND_MESSAGE, skillId, userId)))
        );
    }
}
