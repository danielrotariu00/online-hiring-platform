package com.licenta.database.business.util.validators;

import com.licenta.database.business.models.userdetails.SaveUserDetailsRequest;
import com.licenta.database.business.models.user.AuthenticateUserRequest;
import com.licenta.database.business.models.user.CreateUserRequest;
import com.licenta.database.business.models.user.UpdateUserPasswordRequest;
import com.licenta.database.business.util.exceptions.InvalidInputException;
import org.springframework.stereotype.Component;

// TODO: create InputChecker and add custom message
@Component
public class Validator {

    private static final String INVALID_INPUT_MESSAGE = "Invalid input";

    private void checkNotNull(Object param) {
        if(param == null)
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
    }

    private void checkNotNullOrEmpty(String param) {
        checkNotNull(param);

        if(param.isEmpty())
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
    }

    public void validate(String param) {
        checkNotNullOrEmpty(param);
    }

    public void validate(CreateUserRequest request) {
        checkNotNull(request);
        validate(request.getEmail());
        validate(request.getPassword());
    }

    public void validate(UpdateUserPasswordRequest request) {
        checkNotNull(request);
        validate(request.getPassword());
    }

    public void validate(AuthenticateUserRequest request) {
        checkNotNull(request);
        validate(request.getEmail());
        validate(request.getPassword());
    }

    public void validate(SaveUserDetailsRequest request) {
        checkNotNull(request);
        validate(request.getFirstName());
        validate(request.getLastName());
        validate(request.getPhoneNumber());
        validate(request.getCityName());
        validate(request.getAddress());
        validate(request.getProfilePicture());
        validate(request.getProfileDescription());
    }
}
