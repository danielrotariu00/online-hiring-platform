package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.user.CreateUserRequest;
import com.licenta.databasemicroservice.business.model.user.UserResponse;
import com.licenta.databasemicroservice.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.UUID;

@Mapper(imports = {UUID.class, Date.class})
public interface UserMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    User toModel(CreateUserRequest request);

    UserResponse toResponse(User user);
}
