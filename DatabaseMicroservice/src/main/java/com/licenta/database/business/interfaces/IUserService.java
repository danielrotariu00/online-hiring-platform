package com.licenta.database.business.interfaces;

import com.licenta.database.business.models.user.AuthenticateUserRequest;
import com.licenta.database.business.models.user.CreateUserRequest;
import com.licenta.database.business.models.user.UpdateUserPasswordRequest;
import com.licenta.database.business.models.user.UserResponse;

public interface IUserService {

    void createUser(CreateUserRequest request);
    UserResponse getUser(String id);
    Iterable<UserResponse> getUsers();
    void updateUserPassword(String id, UpdateUserPasswordRequest request);
    void deleteUser(String id);
    void authenticate(AuthenticateUserRequest request);
}