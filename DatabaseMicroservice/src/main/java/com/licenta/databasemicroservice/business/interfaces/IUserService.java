package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.user.AuthenticateUserRequest;
import com.licenta.databasemicroservice.business.model.user.CreateUserRequest;
import com.licenta.databasemicroservice.business.model.user.UpdateUserPasswordRequest;
import com.licenta.databasemicroservice.business.model.user.UserResponse;
import com.licenta.databasemicroservice.persistence.entity.User;

public interface IUserService {

    void createUser(CreateUserRequest request);
    UserResponse getUser(Long userId);
    Iterable<UserResponse> getUsers();
    void updateUserPassword(Long userId, UpdateUserPasswordRequest request);
    void deleteUser(Long userId);
    void authenticate(AuthenticateUserRequest request);
    User getUserOrElseThrowException(Long userId);
}