package com.licenta.jobapplicationmicroservice.business.interfaces;

import com.licenta.jobapplicationmicroservice.business.model.Company;
import com.licenta.jobapplicationmicroservice.business.model.Job;

public interface IDatabaseService {

    void verifyUserExists(Long userId);
    Job getJob(Long jobId);
    Company getCompany(Long companyId);
}
