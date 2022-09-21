package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.IUserService;
import com.licenta.database.business.models.AuthenticateUserRequest;
import com.licenta.database.business.models.CreateUserRequest;
import com.licenta.database.business.models.UpdateUserPasswordRequest;
import com.licenta.database.business.util.converters.UserConverter;
import com.licenta.database.business.util.exceptions.AlreadyExistsException;
import com.licenta.database.business.util.exceptions.FailedAuthenticationException;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.validators.Validator;
import com.licenta.database.persistence.models.UserModel;
import com.licenta.database.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private Validator validator;

    private static final String USER_ALREADY_EXISTS_MESSAGE = "User with username <%s> already exists.";
    private static final String USER_NOT_FOUND_MESSAGE = "User with username <%s> does not exist.";
    private static final String INCORRECT_PASSWORD_MESSAGE = "Incorrect password for user with username <%s>.";

    @Override
    public void addUser(CreateUserRequest request) {
        validator.validate(request);

        String username = request.getUsername();

        if (userRepository.findById(username).isPresent()) {
            throw new AlreadyExistsException(String.format(USER_ALREADY_EXISTS_MESSAGE, username));
        }

        UserModel userModel = userConverter.toModel(request);
        userRepository.save(userModel);
    }

    @Override
    public UserModel getUser(String username) {
        validator.validate(username);

        return userRepository.findById(username).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username))
        );
    }

    @Override
    public Iterable<UserModel> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUserPassword(String username, UpdateUserPasswordRequest request) {
        validator.validate(username);
        validator.validate(request);

        UserModel userModel = userRepository.findById(username).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username))
        );

        userModel.setPassword(request.getPassword());
        userRepository.save(userModel);
    }

    @Override
    public void deleteUser(String username) {
        validator.validate(username);

        userRepository.findById(username).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username))
        );

        userRepository.deleteById(username);
    }

    @Override
    public void authenticate(AuthenticateUserRequest request) {
        validator.validate(request);

        String username = request.getUsername();

        UserModel userModel = userRepository.findById(username).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username))
        );

        if(!userModel.getPassword().equals(request.getPassword()))
            throw new FailedAuthenticationException(String.format(INCORRECT_PASSWORD_MESSAGE, username));
    }
}
