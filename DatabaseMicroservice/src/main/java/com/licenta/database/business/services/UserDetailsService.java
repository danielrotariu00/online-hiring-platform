package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.IUserDetailsService;
import com.licenta.database.business.models.userdetails.SaveUserDetailsRequest;
import com.licenta.database.business.models.userdetails.UserDetailsResponse;
import com.licenta.database.business.util.mappers.UserDetailsMapper;
import com.licenta.database.persistence.models.City;
import com.licenta.database.persistence.models.User;
import com.licenta.database.persistence.models.UserDetails;
import com.licenta.database.persistence.repositories.UserDetailsRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService implements IUserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    private final UserDetailsMapper userDetailsMapper = Mappers.getMapper(UserDetailsMapper.class);

    @Override
    public void saveUserDetails(String userId, SaveUserDetailsRequest request) {
        UserDetails userDetails = userDetailsMapper.toModel(request);

        User user = userService.getUserOrElseThrowException(userId);
        City city = cityService.getCityOrElseThrowException(request.getCityName());

        userDetails.setUser(user);
        userDetails.setCity(city);

        if (userDetailsRepository.findById(userId).isPresent()) {
            userDetails.setUserId(userId);
        }

        userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetailsResponse getUserDetails(String username) {

        userService.getUserOrElseThrowException(username);

        UserDetails  userDetails = userDetailsRepository.findById(username).orElse(null);

        if(userDetails != null)
            return userDetailsMapper.toResponse(userDetails);
        else
            return UserDetailsResponse.builder().build();
    }
}
