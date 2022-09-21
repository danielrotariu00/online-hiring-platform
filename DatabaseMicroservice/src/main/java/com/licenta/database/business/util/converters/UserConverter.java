package com.licenta.database.business.util.converters;

import com.licenta.database.business.models.CreateUserRequest;
import com.licenta.database.persistence.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final ModelMapper mapper = new ModelMapper();

    public UserModel toModel(@NonNull CreateUserRequest request) {

        return mapper.map(request, UserModel.class);
    }
}
