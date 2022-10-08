package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.IUserService;
import com.licenta.database.business.models.user.AuthenticateUserRequest;
import com.licenta.database.business.models.user.CreateUserRequest;
import com.licenta.database.business.models.user.UpdateUserPasswordRequest;
import com.licenta.database.business.models.user.UserResponse;
import com.licenta.database.business.util.converters.UserConverter;
import com.licenta.database.business.util.exceptions.EmailAlreadyInUseException;
import com.licenta.database.business.util.exceptions.FailedAuthenticationException;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.validators.Validator;
import com.licenta.database.persistence.models.User;
import com.licenta.database.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private Validator validator;

    private static final String EMAIL_ALREADY_IN_USE_MESSAGE = "Email <%s> is already in use.";
    protected static final String USER_NOT_FOUND_MESSAGE = "User with id <%s> does not exist.";
    private static final String FAILED_AUTHENTICATION_MESSAGE = "Incorrect email or password.";

    // TODO: encrypt password
    @Override
    public void createUser(CreateUserRequest request) {
        validator.validate(request);

        String email = request.getEmail();

        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new EmailAlreadyInUseException(String.format(EMAIL_ALREADY_IN_USE_MESSAGE, email));
        }

        User user = userConverter.toModel(request);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUser(String id) {
        validator.validate(id);

        User user = getUserOrElseThrowException(id);
        
        return userConverter.toResponse(user);
    }

    @Override
    public Iterable<UserResponse> getUsers() {

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> userConverter.toResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUserPassword(String id, UpdateUserPasswordRequest request) {
        validator.validate(id);
        validator.validate(request);

        User user = getUserOrElseThrowException(id);
        
        user.setPassword(request.getPassword());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        validator.validate(id);

        getUserOrElseThrowException(id);

        userRepository.deleteById(id);
    }

    @Override
    public void authenticate(AuthenticateUserRequest request) {
        validator.validate(request);

        String email = request.getEmail();

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new FailedAuthenticationException(FAILED_AUTHENTICATION_MESSAGE)
        );

        if(!user.getPassword().equals(request.getPassword()))
            throw new FailedAuthenticationException(FAILED_AUTHENTICATION_MESSAGE);
    }
    
    private User getUserOrElseThrowException(String id) {
        
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id))
        );
    }
}
