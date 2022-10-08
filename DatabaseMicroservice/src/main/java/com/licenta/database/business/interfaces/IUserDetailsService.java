package com.licenta.database.business.interfaces;

import com.licenta.database.business.models.userdetails.SaveUserDetailsRequest;
import com.licenta.database.business.models.userdetails.UserDetailsResponse;

public interface IUserDetailsService {

    void saveUserDetails(String userId, SaveUserDetailsRequest request);
    UserDetailsResponse getUserDetails(String userId);
}