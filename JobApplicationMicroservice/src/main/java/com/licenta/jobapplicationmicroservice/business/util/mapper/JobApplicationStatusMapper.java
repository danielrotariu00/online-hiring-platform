package com.licenta.jobapplicationmicroservice.business.util.mapper;

import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplicationStatus;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(imports = {LocalDateTime.class})
public interface JobApplicationStatusMapper {

    JobApplicationStatusResponse toResponse(JobApplicationStatus jobApplicationStatus);
}
