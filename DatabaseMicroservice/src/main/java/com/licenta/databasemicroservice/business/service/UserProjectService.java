package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IUserProjectService;
import com.licenta.databasemicroservice.business.model.UserProjectDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.UserProjectMapper;
import com.licenta.databasemicroservice.persistence.entity.UserProject;
import com.licenta.databasemicroservice.persistence.repository.UserProjectRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProjectService implements IUserProjectService {
    @Autowired
    private UserProjectRepository userProjectRepository;

    private final UserProjectMapper userProjectMapper = Mappers.getMapper(UserProjectMapper.class);

    private static final String USER_PROJECT_NOT_FOUND_MESSAGE = "Project with id <%d> does not exist.";
    @Override
    public UserProjectDTO add(Long userId, UserProjectDTO userProjectDTO) {
        UserProject newUserProject = UserProject.builder()
                .userId(userId)
                .name(userProjectDTO.getName())
                .description(userProjectDTO.getDescription())
                .startDate(userProjectDTO.getStartDate())
                .endDate(userProjectDTO.getEndDate())
                .build();

        return userProjectMapper.toDTO(
                userProjectRepository.save(newUserProject)
        );
    }

    @Override
    public Iterable<UserProjectDTO> getByUserId(Long userId) {
        return userProjectRepository.findAllByUserId(userId).stream()
                .map(userProjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, Long projectId) {

        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndId(userId, projectId);

        userProjectRepository.delete(userProject.orElseThrow(
                () -> new NotFoundException(String.format(USER_PROJECT_NOT_FOUND_MESSAGE, projectId)))
        );
    }
}
