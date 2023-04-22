package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.JobStatusDTO;
import com.licenta.databasemicroservice.persistence.entity.JobStatus;

public interface IJobStatusService {

    JobStatus getJobStatusOrElseThrowException(Integer jobStatusId);

    Iterable<JobStatusDTO> getJobStatusList();

    JobStatusDTO getJobStatus(Integer jobStatusId);
}
