package com.licenta.jobapplicationmicroservice.business.interfaces;

import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplicationStatus;

public interface IJobApplicationStatusService {
    Iterable<JobApplicationStatusResponse> getStatus();
    JobApplicationStatus getStatusOrElseThrowException(Integer statusId);
}
