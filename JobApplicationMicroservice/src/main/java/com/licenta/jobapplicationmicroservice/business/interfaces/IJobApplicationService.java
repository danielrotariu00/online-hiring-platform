package com.licenta.jobapplicationmicroservice.business.interfaces;

import com.licenta.jobapplicationmicroservice.business.model.CreateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.business.model.UpdateJobApplicationRequest;

public interface IJobApplicationService {

    void create(CreateJobApplicationRequest request);
    JobApplicationResponse getById(Long jobApplicationId);
    Iterable<JobApplicationResponse> getByUserId(Long userId);
    Iterable<JobApplicationResponse> getByJobId(Long jobId);
    void update(Long jobApplicationId, UpdateJobApplicationRequest request);
    void delete(Long jobApplicationId);
    Iterable<JobApplicationStatusResponse> getStatus();

    JobApplicationStatusResponse getStatusById(Integer statusId);
}
