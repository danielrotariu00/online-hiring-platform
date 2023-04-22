package com.licenta.databasemicroservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_details")
public class UserDetails {

    @Id
    private Long userId;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    private String phoneNumber;
    private String address;
    private String profileDescription;
    private String profilePictureUrl;
    private Integer cityId;
}
