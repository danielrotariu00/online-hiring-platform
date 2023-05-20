package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.UserDetailsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserDetailsService {

    UserDetailsDTO saveUserDetails(Long userId, UserDetailsDTO request);
    UserDetailsDTO getUserDetails(Long userId);

    void saveImage(Long userId, MultipartFile image) throws IOException;

    void deleteUserDetails(Long userId);
}