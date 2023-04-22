package com.licenta.jobapplicationmicroservice.business.interfaces;

import com.licenta.jobapplicationmicroservice.business.model.CreateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.model.FileData;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.business.model.Message;
import com.licenta.jobapplicationmicroservice.business.model.RecruiterStatistics;
import com.licenta.jobapplicationmicroservice.business.model.Review;
import com.licenta.jobapplicationmicroservice.business.model.UpdateJobApplicationRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface IJobApplicationService {

    void create(Long userId, CreateJobApplicationRequest request);
    JobApplicationResponse getById(String jobApplicationId);
    Iterable<JobApplicationResponse> getByUserId(Long userId);
    Iterable<JobApplicationResponse> getByJobId(Long jobId);
    void update(String jobApplicationId, UpdateJobApplicationRequest request);
    void delete(String jobApplicationId);
    Iterable<JobApplicationStatusResponse> getStatus();

    JobApplicationStatusResponse getStatusById(Integer statusId);

    Message addMessage(String jobApplicationId, Message message);

    Review updateReview(Long userId, String jobApplicationId, Review review);

    RecruiterStatistics getRecruiterStatistics(Long recruiterId);

    List<FileData> getFileList(String jobApplicationId);

    Resource download(String jobApplicationId, String filename);
}
