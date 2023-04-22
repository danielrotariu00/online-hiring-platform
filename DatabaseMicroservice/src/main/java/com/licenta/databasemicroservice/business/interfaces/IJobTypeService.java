package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.JobTypeDTO;
import com.licenta.databasemicroservice.persistence.entity.JobType;

public interface IJobTypeService {

    JobType getJobTypeOrElseThrowException(Integer jobTypeId);

    Iterable<JobTypeDTO> getJobTypes();

    JobTypeDTO getJobType(Integer jobTypeId);
}
