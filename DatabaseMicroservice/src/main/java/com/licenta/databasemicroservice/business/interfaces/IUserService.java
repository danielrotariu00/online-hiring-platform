package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.user.AuthenticateUserRequest;
import com.licenta.databasemicroservice.business.model.user.CreateUserRequest;
import com.licenta.databasemicroservice.business.model.user.UpdateUserPasswordRequest;
import com.licenta.databasemicroservice.business.model.user.UserResponse;

public interface IUserService {

    void createUser(CreateUserRequest request);
    UserResponse getUser(String userId);
    Iterable<UserResponse> getUsers();
    void updateUserPassword(String userId, UpdateUserPasswordRequest request);
    void deleteUser(String userId);
    void authenticate(AuthenticateUserRequest request);
}