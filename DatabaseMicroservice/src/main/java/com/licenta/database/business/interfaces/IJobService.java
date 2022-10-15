package com.licenta.database.business.interfaces;

import com.licenta.database.business.models.job.JobRequest;
import com.licenta.database.business.models.job.JobResponse;

public interface IJobService {

    void createJob(JobRequest request);
    JobResponse getJob(String jobId);
    Iterable<JobResponse> getJobs();
    void updateJob(String jobId, JobRequest request);
    void deleteJob(String jobId);
}