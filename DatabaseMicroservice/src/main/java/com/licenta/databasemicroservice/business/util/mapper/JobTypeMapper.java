package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.jobtype.JobTypeResponse;
import com.licenta.databasemicroservice.persistence.entity.JobType;
import org.mapstruct.Mapper;

@Mapper
public interface JobTypeMapper {

    JobTypeResponse toResponse(JobType jobType);
}
