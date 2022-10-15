package com.licenta.database.business.interfaces;

import com.licenta.database.business.models.user.AuthenticateUserRequest;
import com.licenta.database.business.models.user.CreateUserRequest;
import com.licenta.database.business.models.user.UpdateUserPasswordRequest;
import com.licenta.database.business.models.user.UserResponse;

public interface IUserService {

    void createUser(CreateUserRequest request);
    UserResponse getUser(String userId);
    Iterable<UserResponse> getUsers();
    void updateUserPassword(String userId, UpdateUserPasswordRequest request);
    void deleteUser(String userId);
    void authenticate(AuthenticateUserRequest request);
}