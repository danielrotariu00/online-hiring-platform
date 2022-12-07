package com.licenta.jobapplicationmicroservice.business.util.mapper;

import com.licenta.jobapplicationmicroservice.business.model.CreateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(imports = {LocalDateTime.class})
public interface JobApplicationMapper {

    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    JobApplication toEntity(CreateJobApplicationRequest request);

    @Mapping(target="statusId", source="status.id")
    JobApplicationResponse toResponse(JobApplication jobApplication);
}
