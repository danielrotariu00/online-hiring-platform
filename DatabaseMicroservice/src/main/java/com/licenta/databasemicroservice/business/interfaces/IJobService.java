package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.job.JobRequest;
import com.licenta.databasemicroservice.business.model.job.JobResponse;

public interface IJobService {

    void createJob(JobRequest request);
    JobResponse getJob(Long jobId);
    Iterable<JobResponse> getJobs();
    Iterable<JobResponse> getCompanyIndustryJobs(Long companyIndustryId);
    void updateJob(Long jobId, JobRequest request);
    void deleteJob(Long jobId);
}