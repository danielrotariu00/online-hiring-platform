package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserProjectDTO;

public interface IUserProjectService {
    void add(UserProjectDTO userProjectDTO);
    void delete(Long userProjectId);
    Iterable<UserProjectDTO> getByUserId(Long userId);
}
