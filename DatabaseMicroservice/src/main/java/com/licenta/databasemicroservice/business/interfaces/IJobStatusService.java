package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.jobstatus.JobStatusResponse;
import com.licenta.databasemicroservice.persistence.entity.JobStatus;

public interface IJobStatusService {

    JobStatus getJobStatusOrElseThrowException(Integer jobStatusId);

    Iterable<JobStatusResponse> getJobStatusList();

    JobStatusResponse getJobStatus(Integer jobStatusId);
}
