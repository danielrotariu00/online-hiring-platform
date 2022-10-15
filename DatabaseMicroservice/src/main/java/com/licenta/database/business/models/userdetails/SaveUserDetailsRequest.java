package com.licenta.database.business.models.userdetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class SaveUserDetailsRequest {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String cityName;
    @NotEmpty
    private String address;
    @NotEmpty
    private String profileDescription;
    @NotEmpty
    private String profilePicture;
}
