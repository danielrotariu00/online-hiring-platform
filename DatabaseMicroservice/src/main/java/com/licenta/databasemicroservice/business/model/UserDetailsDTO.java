package com.licenta.databasemicroservice.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {

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
