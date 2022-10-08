package com.licenta.database.business.util.converters;

import com.licenta.database.business.models.userdetails.SaveUserDetailsRequest;
import com.licenta.database.business.models.userdetails.UserDetailsResponse;
import com.licenta.database.persistence.models.UserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsConverter {

    private final ModelMapper mapper = new ModelMapper();

    public UserDetails toModel(@NonNull SaveUserDetailsRequest request) {

        return mapper.map(request, UserDetails.class);
    }

    public UserDetailsResponse toResponse(@NonNull UserDetails userDetails) {
        return UserDetailsResponse.builder()
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .phoneNumber(userDetails.getPhoneNumber())
                .cityName(userDetails.getCity().getName())
                .address(userDetails.getAddress())
                .profileDescription(userDetails.getProfileDescription())
                .profilePicture(userDetails.getProfilePicture())
                .build();
    }
}
