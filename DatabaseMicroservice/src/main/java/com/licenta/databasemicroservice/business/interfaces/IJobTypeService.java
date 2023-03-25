package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.jobtype.JobTypeResponse;
import com.licenta.databasemicroservice.persistence.entity.JobType;

public interface IJobTypeService {

    JobType getJobTypeOrElseThrowException(Integer jobTypeId);

    Iterable<JobTypeResponse> getJobTypes();

    JobTypeResponse getJobType(Integer jobTypeId);
}
