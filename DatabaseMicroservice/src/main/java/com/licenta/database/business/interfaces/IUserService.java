package com.licenta.database.business.interfaces;

import com.licenta.database.business.models.AuthenticateUserRequest;
import com.licenta.database.business.models.CreateUserRequest;
import com.licenta.database.business.models.UpdateUserPasswordRequest;
import com.licenta.database.persistence.models.UserModel;

public interface IUserService {

    void addUser(CreateUserRequest request);
    UserModel getUser(String username);
    Iterable<UserModel> getUsers();
    void updateUserPassword(String username, UpdateUserPasswordRequest request);
    void deleteUser(String username);
    void authenticate(AuthenticateUserRequest request);
}