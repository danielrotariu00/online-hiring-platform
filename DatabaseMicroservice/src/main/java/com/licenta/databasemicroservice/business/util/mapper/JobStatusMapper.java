package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.JobStatusDTO;
import com.licenta.databasemicroservice.persistence.entity.JobStatus;
import org.mapstruct.Mapper;

@Mapper
public interface JobStatusMapper {

    JobStatusDTO toResponse(JobStatus jobStatus);
}
