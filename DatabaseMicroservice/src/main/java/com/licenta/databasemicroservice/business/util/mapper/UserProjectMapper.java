package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.UserProjectDTO;
import com.licenta.databasemicroservice.persistence.entity.UserProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserProjectMapper {

    @Mapping(target="userId", source="user.id")
    UserProjectDTO toDTO(UserProject userProject);
}
