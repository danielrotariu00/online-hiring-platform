package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IEducationalInstitutionService;
import com.licenta.databasemicroservice.business.interfaces.IUserEducationalExperienceService;
import com.licenta.databasemicroservice.business.interfaces.IUserService;
import com.licenta.databasemicroservice.business.model.UserEducationalExperienceDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.UserEducationalExperienceMapper;
import com.licenta.databasemicroservice.persistence.entity.EducationalInstitution;
import com.licenta.databasemicroservice.persistence.entity.User;
import com.licenta.databasemicroservice.persistence.entity.UserEducationalExperience;
import com.licenta.databasemicroservice.persistence.repository.UserEducationalExperienceRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserEducationalExperienceService implements IUserEducationalExperienceService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEducationalInstitutionService educationalInstitutionService;

    @Autowired
    private UserEducationalExperienceRepository userEducationalExperienceRepository;

    private final UserEducationalExperienceMapper userEducationalExperienceMapper = Mappers.getMapper(UserEducationalExperienceMapper.class);

    private static final String USER_EDUCATIONAL_EXPERIENCE_NOT_FOUND_MESSAGE = "Educational Experience with id <%d> does not exist.";
    @Override
    public UserEducationalExperienceDTO add(UserEducationalExperienceDTO userEducationalExperienceDTO) {
        Long userId = userEducationalExperienceDTO.getUserId();
        Long educationalInstitutionId = userEducationalExperienceDTO.getEducationalInstitutionId();

        User user = userService.getUserOrElseThrowException(userId);
        EducationalInstitution educationalInstitution = educationalInstitutionService.getOrElseThrowException(educationalInstitutionId);


        UserEducationalExperience newUserEducationalExperience = UserEducationalExperience.builder()
                .user(user)
                .educationalInstitution(educationalInstitution)
                .speciality(userEducationalExperienceDTO.getSpeciality())
                .title(userEducationalExperienceDTO.getTitle())
                .startDate(userEducationalExperienceDTO.getStartDate())
                .endDate(userEducationalExperienceDTO.getEndDate())
                .build();

        return userEducationalExperienceMapper.toDTO(
                userEducationalExperienceRepository.save(newUserEducationalExperience)
        );
    }

    @Override
    public Iterable<UserEducationalExperienceDTO> getByUserId(Long userId) {
        userService.getUserOrElseThrowException(userId);

        return userEducationalExperienceRepository.findAllByUserId(userId).stream()
                .map(userEducationalExperienceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long educationalExperienceId) {

        Optional<UserEducationalExperience> userEducationalExperience =
                userEducationalExperienceRepository.findById(educationalExperienceId);

        userEducationalExperienceRepository.delete(userEducationalExperience.orElseThrow(
                () -> new NotFoundException(String.format(USER_EDUCATIONAL_EXPERIENCE_NOT_FOUND_MESSAGE, educationalExperienceId)))
        );
    }
}
