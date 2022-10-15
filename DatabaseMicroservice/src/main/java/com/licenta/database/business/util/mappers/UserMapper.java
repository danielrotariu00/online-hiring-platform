package com.licenta.database.business.util.mappers;

import com.licenta.database.business.models.user.CreateUserRequest;
import com.licenta.database.business.models.user.UserResponse;
import com.licenta.database.persistence.models.User;
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
