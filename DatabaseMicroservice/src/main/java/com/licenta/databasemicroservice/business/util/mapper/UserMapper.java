package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.user.CreateUserRequest;
import com.licenta.databasemicroservice.business.model.user.UserResponse;
import com.licenta.databasemicroservice.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toModel(CreateUserRequest request);

    UserResponse toResponse(User user);
}
