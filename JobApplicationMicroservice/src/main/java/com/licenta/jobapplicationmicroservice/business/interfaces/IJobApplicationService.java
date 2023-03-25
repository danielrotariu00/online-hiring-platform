package com.licenta.jobapplicationmicroservice.business.interfaces;

import com.licenta.jobapplicationmicroservice.business.model.CreateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.business.model.Message;
import com.licenta.jobapplicationmicroservice.business.model.Review;
import com.licenta.jobapplicationmicroservice.business.model.UpdateJobApplicationRequest;

public interface IJobApplicationService {

    void create(CreateJobApplicationRequest request);
    JobApplicationResponse getById(String jobApplicationId);
    Iterable<JobApplicationResponse> getByUserId(Long userId);
    Iterable<JobApplicationResponse> getByJobId(Long jobId);
    void update(String jobApplicationId, UpdateJobApplicationRequest request);
    void delete(String jobApplicationId);
    Iterable<JobApplicationStatusResponse> getStatus();

    JobApplicationStatusResponse getStatusById(Integer statusId);

    Message addMessage(String jobApplicationId, Message message);

    Review updateReview(String jobApplicationId, Review review);
}
