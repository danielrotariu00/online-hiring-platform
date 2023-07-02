package com.licenta.jobapplicationmicroservice.business.interfaces;

import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.persistence.document.JobApplicationStatus;

public interface IJobApplicationStatusService {
    Iterable<JobApplicationStatusResponse> getStatus();

    JobApplicationStatusResponse getStatusById(Integer statusId);

    JobApplicationStatus getStatusOrElseThrowException(Integer statusId);
}
