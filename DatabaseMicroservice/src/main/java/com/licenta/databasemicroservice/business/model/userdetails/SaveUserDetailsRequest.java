package com.licenta.databasemicroservice.business.model.userdetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
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
    @Min(1)
    private Integer cityId;
    @NotEmpty
    private String address;
    @NotEmpty
    private String profileDescription;
    @NotEmpty
    private String profilePictureUrl;
}
