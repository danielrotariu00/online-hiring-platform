package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserProjectDTO;

public interface IUserProjectService {
    UserProjectDTO add(Long userId, UserProjectDTO userProjectDTO);
    void delete(Long userId, Long userProjectId);
    Iterable<UserProjectDTO> getByUserId(Long userId);
}
