package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.userdetails.SaveUserDetailsRequest;
import com.licenta.databasemicroservice.business.model.userdetails.UserDetailsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserDetailsService {

    UserDetailsResponse saveUserDetails(Long userId, SaveUserDetailsRequest request);
    UserDetailsResponse getUserDetails(Long userId);

    void saveImage(Long userId, MultipartFile image) throws IOException;

    void deleteUserDetails(Long userId);
}