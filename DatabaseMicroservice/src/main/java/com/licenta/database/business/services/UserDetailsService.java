package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.IUserDetailsService;
import com.licenta.database.business.models.userdetails.SaveUserDetailsRequest;
import com.licenta.database.business.models.userdetails.UserDetailsResponse;
import com.licenta.database.business.util.converters.UserDetailsConverter;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.validators.Validator;
import com.licenta.database.persistence.models.City;
import com.licenta.database.persistence.models.User;
import com.licenta.database.persistence.models.UserDetails;
import com.licenta.database.persistence.repositories.CityRepository;
import com.licenta.database.persistence.repositories.UserDetailsRepository;
import com.licenta.database.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.licenta.database.business.services.UserService.USER_NOT_FOUND_MESSAGE;

@Service
public class UserDetailsService implements IUserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private UserDetailsConverter userDetailsConverter;
    @Autowired
    private Validator validator;

    private static final String CITY_NOT_FOUND_MESSAGE = "City <%s> does not exist.";

    @Override
    public void saveUserDetails(String userId, SaveUserDetailsRequest request) {
        validator.validate(userId);
        validator.validate(request);

        UserDetails userDetails = userDetailsConverter.toModel(request);

        User user = getUserOrElseThrowException(userId);
        City city = getCityOrElseThrowException(request.getCityName());

        userDetails.setUser(user);
        userDetails.setCity(city);

        if (userDetailsRepository.findById(userId).isPresent()) {
            userDetails.setUserId(userId);
        }

        userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetailsResponse getUserDetails(String username) {
        validator.validate(username);

        getUserOrElseThrowException(username);

        UserDetails  userDetails = userDetailsRepository.findById(username).orElse(null);

        if(userDetails != null)
            return userDetailsConverter.toResponse(userDetails);
        else
            return UserDetailsResponse.builder().build();
    }

    private User getUserOrElseThrowException(String username) {

        return userRepository.findById(username).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username))
        );
    }

    private City getCityOrElseThrowException(String cityName) {

        return cityRepository.findById(cityName).orElseThrow(
                () -> new NotFoundException(String.format(CITY_NOT_FOUND_MESSAGE, cityName))
        );
    }
}
