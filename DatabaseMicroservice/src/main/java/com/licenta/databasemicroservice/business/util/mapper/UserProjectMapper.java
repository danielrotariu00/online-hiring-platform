package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.UserProjectDTO;
import com.licenta.databasemicroservice.persistence.entity.UserProject;
import org.mapstruct.Mapper;

@Mapper
public interface UserProjectMapper {

    UserProjectDTO toDTO(UserProject userProject);
}
