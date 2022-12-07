package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.userdetails.SaveUserDetailsRequest;
import com.licenta.databasemicroservice.business.model.userdetails.UserDetailsResponse;

public interface IUserDetailsService {

    void saveUserDetails(Long userId, SaveUserDetailsRequest request);
    UserDetailsResponse getUserDetails(Long userId);
}