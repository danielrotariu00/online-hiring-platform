package com.licenta.databasemicroservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
    @NotEmpty
    private String userId;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String address;
    @NotEmpty
    private String profileDescription;
    @NotEmpty
    private String profilePicture;

    // Foreign keys
    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable=false)
    private City city;
}
