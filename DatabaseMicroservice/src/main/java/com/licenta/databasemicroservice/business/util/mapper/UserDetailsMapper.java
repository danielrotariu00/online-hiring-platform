package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.userdetails.SaveUserDetailsRequest;
import com.licenta.databasemicroservice.business.model.userdetails.UserDetailsResponse;
import com.licenta.databasemicroservice.persistence.entity.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.UUID;

@Mapper(imports = {UUID.class, Date.class})
public interface UserDetailsMapper {

    UserDetails toModel(SaveUserDetailsRequest request);

    @Mapping(target="cityId", source="city.id")
    UserDetailsResponse toResponse(UserDetails userDetails);
}
