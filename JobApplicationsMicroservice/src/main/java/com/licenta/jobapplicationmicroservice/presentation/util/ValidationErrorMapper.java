package com.licenta.jobapplicationmicroservice.presentation.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.validation.FieldError;

@Mapper
public interface ValidationErrorMapper {

    @Mapping(target="message", source="defaultMessage")
    ValidationError toValidationError(FieldError fieldError);
}
