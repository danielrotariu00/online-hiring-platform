package com.licenta.jobapplicationmicroservice.presentation.controller;

import com.licenta.jobapplicationmicroservice.business.interfaces.IJobApplicationService;
import com.licenta.jobapplicationmicroservice.business.model.CreateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.business.model.UpdateJobApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
public class JobApplicationController {

    @Autowired
    private IJobApplicationService jobApplicationService;

    @RequestMapping(value="/job-applications", method = RequestMethod.POST)
    public void createJobApplication(@Valid @RequestBody CreateJobApplicationRequest request) {

        jobApplicationService.create(request);
    }

    @RequestMapping(value = "/job-applications/{jobApplicationId}", method = RequestMethod.GET)
    public JobApplicationResponse getById(@Min(1) @PathVariable Long jobApplicationId) {

        return jobApplicationService.getById(jobApplicationId);
    }

    @RequestMapping(value="/users/{userId}/job-applications", method = RequestMethod.GET)
    public Iterable<JobApplicationResponse> getByUserId(@Min(1) @PathVariable Long userId) {

        return jobApplicationService.getByUserId(userId);
    }

    @RequestMapping(value="/jobs/{jobId}/job-applications", method = RequestMethod.GET)
    public Iterable<JobApplicationResponse> getByJobId(@Min(1) @PathVariable Long jobId) {

        return jobApplicationService.getByJobId(jobId);
    }

    @RequestMapping(value = "/job-applications/{jobApplicationId}", method = RequestMethod.PUT)
    public void updateJobApplication(
            @Min(1) @PathVariable Long jobApplicationId,
            @Valid @RequestBody UpdateJobApplicationRequest request) {

        jobApplicationService.update(jobApplicationId, request);
    }

    @RequestMapping(value = "/job-applications/{jobApplicationId}", method = RequestMethod.DELETE)
    public void deleteJobApplication(@Min(1) @PathVariable Long jobApplicationId) {

        jobApplicationService.delete(jobApplicationId);
    }

    @RequestMapping(value = "/job-applications/status", method = RequestMethod.GET)
    public Iterable<JobApplicationStatusResponse> getStatus() {

        return jobApplicationService.getStatus();
    }
}