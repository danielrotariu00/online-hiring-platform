package com.licenta.database.business.util.mappers;

import com.licenta.database.business.model.userdetails.SaveUserDetailsRequest;
import com.licenta.database.business.model.userdetails.UserDetailsResponse;
import com.licenta.database.persistence.entities.UserDetails;
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
