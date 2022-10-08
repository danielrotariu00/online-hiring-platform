package com.licenta.database.business.models.userdetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDetailsResponse {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String cityName;
    private String address;
    private String profileDescription;
    private String profilePicture;
}
