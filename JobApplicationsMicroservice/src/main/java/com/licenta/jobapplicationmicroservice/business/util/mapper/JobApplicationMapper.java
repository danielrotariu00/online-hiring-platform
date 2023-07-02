package com.licenta.jobapplicationmicroservice.business.util.mapper;

import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.persistence.document.JobApplication;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(imports = {LocalDateTime.class})
public interface JobApplicationMapper {

    JobApplicationResponse toResponse(JobApplication jobApplication);
}
