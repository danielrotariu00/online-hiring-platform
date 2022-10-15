package com.licenta.database.presentation.controllers;

import com.licenta.database.business.interfaces.IJobService;
import com.licenta.database.business.models.job.JobRequest;
import com.licenta.database.business.models.job.JobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Validated
@RestController
@RequestMapping(value="/jobs")
public class JobController {

    @Autowired
    private IJobService jobService;

    @RequestMapping(method=RequestMethod.POST)
    public void createJob (@Valid @RequestBody JobRequest request) {

        jobService.createJob(request);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<JobResponse> getAllJobs() {

        return jobService.getJobs();
    }

    @RequestMapping(value="/{jobId}", method=RequestMethod.GET)
    public JobResponse getJob(@NotEmpty @PathVariable String jobId) {

        return jobService.getJob(jobId);
    }

    @RequestMapping(value="/{jobId}", method=RequestMethod.PUT)
    public void updateJob(@NotEmpty @PathVariable String jobId, @Valid @RequestBody JobRequest request) {

        jobService.updateJob(jobId, request);
    }

    @RequestMapping(value="/{jobId}", method=RequestMethod.DELETE)
    public void deleteJob(@NotEmpty @PathVariable String jobId) {

        jobService.deleteJob(jobId);
    }
}