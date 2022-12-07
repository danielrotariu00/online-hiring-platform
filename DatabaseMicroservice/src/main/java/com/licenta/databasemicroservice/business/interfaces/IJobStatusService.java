package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.persistence.entity.JobStatus;

public interface IJobStatusService {

    JobStatus getJobStatusOrElseThrowException(Integer jobStatusId);
}
