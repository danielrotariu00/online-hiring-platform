package com.licenta.database.business.util.validators;

import com.licenta.database.business.models.AuthenticateUserRequest;
import com.licenta.database.business.models.CreateUserRequest;
import com.licenta.database.business.models.UpdateUserPasswordRequest;
import com.licenta.database.business.util.exceptions.InvalidInputException;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    private static final String INVALID_INPUT_MESSAGE = "Invalid input";

    public void validate(String param) {
        checkNotNull(param);
        checkNotBlank(param);
    }

    public void validate(CreateUserRequest request) {
        checkNotNull(request);
        validate(request.getUsername());
        validate(request.getPassword());
    }

    public void validate(UpdateUserPasswordRequest request) {
        checkNotNull(request);
        validate(request.getPassword());
    }

    public void validate(AuthenticateUserRequest request) {
        checkNotNull(request);
        validate(request.getUsername());
        validate(request.getPassword());
    }

    private void checkNotNull(Object param) {
        if(param == null)
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
    }

    private void checkNotBlank(String param) {
        if(param.isBlank())
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
    }
}
