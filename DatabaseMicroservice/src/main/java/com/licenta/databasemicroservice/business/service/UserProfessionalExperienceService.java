package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICompanyService;
import com.licenta.databasemicroservice.business.interfaces.IUserProfessionalExperienceService;
import com.licenta.databasemicroservice.business.model.UserProfessionalExperienceDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.UserProfessionalExperienceMapper;
import com.licenta.databasemicroservice.persistence.entity.Company;
import com.licenta.databasemicroservice.persistence.entity.UserProfessionalExperience;
import com.licenta.databasemicroservice.persistence.repository.UserProfessionalExperienceRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfessionalExperienceService implements IUserProfessionalExperienceService {
    @Autowired
    private ICompanyService companyService;

    @Autowired
    private UserProfessionalExperienceRepository userProfessionalExperienceRepository;

    private final UserProfessionalExperienceMapper userProfessionalExperienceMapper = Mappers.getMapper(UserProfessionalExperienceMapper.class);

    private static final String USER_PROFESSIONAL_EXPERIENCE_NOT_FOUND_MESSAGE = "Professional Experience with id <%d> does not exist.";
    @Override
    public UserProfessionalExperienceDTO add(Long userId, UserProfessionalExperienceDTO userProfessionalExperienceDTO) {
        Long CompanyId = userProfessionalExperienceDTO.getCompanyId();

        Company Company = companyService.getCompanyOrElseThrowException(CompanyId);


        UserProfessionalExperience newUserProfessionalExperience = UserProfessionalExperience.builder()
                .userId(userId)
                .company(Company)
                .jobTitle(userProfessionalExperienceDTO.getJobTitle())
                .description(userProfessionalExperienceDTO.getDescription())
                .startDate(userProfessionalExperienceDTO.getStartDate())
                .endDate(userProfessionalExperienceDTO.getEndDate())
                .build();

        return userProfessionalExperienceMapper.toDTO(
                userProfessionalExperienceRepository.save(newUserProfessionalExperience)
        );
    }

    @Override
    public UserProfessionalExperienceDTO update(Long userId, Long id, UserProfessionalExperienceDTO userProfessionalExperienceDTO) {
        Long CompanyId = userProfessionalExperienceDTO.getCompanyId();

        userProfessionalExperienceRepository.findByUserIdAndId(userId, id).orElseThrow(
                () -> new NotFoundException(String.format(USER_PROFESSIONAL_EXPERIENCE_NOT_FOUND_MESSAGE, id))
        );

        Company Company = companyService.getCompanyOrElseThrowException(CompanyId);


        UserProfessionalExperience userProfessionalExperience = UserProfessionalExperience.builder()
                .userId(userId)
                .company(Company)
                .jobTitle(userProfessionalExperienceDTO.getJobTitle())
                .description(userProfessionalExperienceDTO.getDescription())
                .startDate(userProfessionalExperienceDTO.getStartDate())
                .endDate(userProfessionalExperienceDTO.getEndDate())
                .build();

        return userProfessionalExperienceMapper.toDTO(
                userProfessionalExperienceRepository.save(userProfessionalExperience)
        );
    }

    @Override
    public Iterable<UserProfessionalExperienceDTO> getByUserId(Long userId) {
        return userProfessionalExperienceRepository.findAllByUserId(userId).stream()
                .map(userProfessionalExperienceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, Long professionalExperienceId) {

        Optional<UserProfessionalExperience> userProfessionalExperience =
                userProfessionalExperienceRepository.findByUserIdAndId(userId, professionalExperienceId);

        userProfessionalExperienceRepository.delete(userProfessionalExperience.orElseThrow(
                () -> new NotFoundException(String.format(USER_PROFESSIONAL_EXPERIENCE_NOT_FOUND_MESSAGE, professionalExperienceId)))
        );
    }
}
