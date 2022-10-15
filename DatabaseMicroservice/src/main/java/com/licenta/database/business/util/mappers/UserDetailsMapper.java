package com.licenta.database.business.util.mappers;

import com.licenta.database.business.models.userdetails.SaveUserDetailsRequest;
import com.licenta.database.business.models.userdetails.UserDetailsResponse;
import com.licenta.database.persistence.models.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.UUID;

@Mapper(imports = {UUID.class, Date.class})
public interface UserDetailsMapper {

    UserDetails toModel(SaveUserDetailsRequest request);

    @Mapping(target="cityName", source="city.name")
    UserDetailsResponse toResponse(UserDetails userDetails);
}
