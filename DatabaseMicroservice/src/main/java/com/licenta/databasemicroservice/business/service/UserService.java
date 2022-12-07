package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IUserService;
import com.licenta.databasemicroservice.business.model.user.AuthenticateUserRequest;
import com.licenta.databasemicroservice.business.model.user.CreateUserRequest;
import com.licenta.databasemicroservice.business.model.user.UpdateUserPasswordRequest;
import com.licenta.databasemicroservice.business.model.user.UserResponse;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.FailedAuthenticationException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.UserMapper;
import com.licenta.databasemicroservice.persistence.entity.User;
import com.licenta.databasemicroservice.persistence.repository.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private static final String EMAIL_ALREADY_IN_USE_MESSAGE = "Email <%s> is already in use.";
    private static final String USER_NOT_FOUND_MESSAGE = "User with id <%s> does not exist.";
    private static final String FAILED_AUTHENTICATION_MESSAGE = "Incorrect email or password.";

    // TODO: encrypt password
    @Override
    public void createUser(CreateUserRequest request) {
        String email = request.getEmail();

        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new AlreadyExistsException(String.format(EMAIL_ALREADY_IN_USE_MESSAGE, email));
        }

        User user = userMapper.toModel(request);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = getUserOrElseThrowException(id);
        
        return userMapper.toResponse(user);
    }

    @Override
    public Iterable<UserResponse> getUsers() {

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUserPassword(Long id, UpdateUserPasswordRequest request) {
        User user = getUserOrElseThrowException(id);
        
        user.setPassword(request.getPassword());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        getUserOrElseThrowException(id);

        userRepository.deleteById(id);
    }

    @Override
    public void authenticate(AuthenticateUserRequest request) {
        String email = request.getEmail();

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new FailedAuthenticationException(FAILED_AUTHENTICATION_MESSAGE)
        );

        if(!user.getPassword().equals(request.getPassword()))
            throw new FailedAuthenticationException(FAILED_AUTHENTICATION_MESSAGE);
    }
    
    public User getUserOrElseThrowException(Long id) {
        
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id))
        );
    }
}
