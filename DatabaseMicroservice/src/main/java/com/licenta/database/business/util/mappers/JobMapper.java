package com.licenta.database.business.util.mappers;

import com.licenta.database.business.models.job.JobRequest;
import com.licenta.database.business.models.job.JobResponse;
import com.licenta.database.persistence.models.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Date;
import java.util.UUID;

@Mapper(imports = {UUID.class, Date.class})
public interface JobMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "createdAt", expression = "java(new Date().toString())")
    Job toModel(JobRequest request);

    @Mapping(target="companyId", source="company.id")
    @Mapping(target="cityName", source="city.name")
    @Mapping(target="countryName", source="country.name")
    JobResponse toResponse(Job job);
}
