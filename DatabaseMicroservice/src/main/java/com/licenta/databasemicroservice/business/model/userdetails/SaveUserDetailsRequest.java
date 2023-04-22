package com.licenta.databasemicroservice.business.model.userdetails;

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

    private String phoneNumber;
    private Integer cityId;
    private String address;
    private String profileDescription;
    private String profilePictureUrl;
}
