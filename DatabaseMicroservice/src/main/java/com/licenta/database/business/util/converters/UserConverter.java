package com.licenta.database.business.util.converters;

import com.licenta.database.business.models.user.CreateUserRequest;
import com.licenta.database.business.models.user.UserResponse;
import com.licenta.database.persistence.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final ModelMapper mapper = new ModelMapper();

    public User toModel(@NonNull CreateUserRequest request) {

        return mapper.map(request, User.class);
    }

    public UserResponse toResponse(@NonNull User user) {

        return mapper.map(user, UserResponse.class);
    }
}
