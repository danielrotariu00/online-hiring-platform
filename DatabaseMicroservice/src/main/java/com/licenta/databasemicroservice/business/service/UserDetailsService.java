package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICityService;
import com.licenta.databasemicroservice.business.interfaces.IUserDetailsService;
import com.licenta.databasemicroservice.business.interfaces.IUserService;
import com.licenta.databasemicroservice.business.model.userdetails.SaveUserDetailsRequest;
import com.licenta.databasemicroservice.business.model.userdetails.UserDetailsResponse;
import com.licenta.databasemicroservice.business.util.mapper.UserDetailsMapper;
import com.licenta.databasemicroservice.persistence.entity.City;
import com.licenta.databasemicroservice.persistence.entity.User;
import com.licenta.databasemicroservice.persistence.entity.UserDetails;
import com.licenta.databasemicroservice.persistence.repository.UserDetailsRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService implements IUserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    private final UserDetailsMapper userDetailsMapper = Mappers.getMapper(UserDetailsMapper.class);

    @Override
    public void saveUserDetails(Long userId, SaveUserDetailsRequest request) {
        UserDetails userDetails = userDetailsMapper.toModel(request);

        User user = userService.getUserOrElseThrowException(userId);
        City city = cityService.getCityOrElseThrowException(request.getCityId());

        userDetails.setUser(user);
        userDetails.setCity(city);

        if (userDetailsRepository.findById(userId).isPresent()) {
            userDetails.setUserId(userId);
        }

        userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetailsResponse getUserDetails(Long userId) {

        userService.getUserOrElseThrowException(userId);

        UserDetails  userDetails = userDetailsRepository.findById(userId).orElse(null);

        if(userDetails != null)
            return userDetailsMapper.toResponse(userDetails);
        else
            return UserDetailsResponse.builder().build();
    }
}
