package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.JobTypeDTO;
import com.licenta.databasemicroservice.persistence.entity.JobType;
import org.mapstruct.Mapper;

@Mapper
public interface JobTypeMapper {

    JobTypeDTO toResponse(JobType jobType);
}
