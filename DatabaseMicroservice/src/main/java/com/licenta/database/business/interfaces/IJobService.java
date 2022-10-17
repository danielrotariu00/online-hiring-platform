package com.licenta.database.business.interfaces;

import com.licenta.database.business.model.job.JobRequest;
import com.licenta.database.business.model.job.JobResponse;

public interface IJobService {

    void createJob(JobRequest request);
    JobResponse getJob(String jobId);
    Iterable<JobResponse> getJobs();
    Iterable<JobResponse> getCompanyIndustryJobs(Integer companyIndustryId);
    void updateJob(String jobId, JobRequest request);
    void deleteJob(String jobId);
}