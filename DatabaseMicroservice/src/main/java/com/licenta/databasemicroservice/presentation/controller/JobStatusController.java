package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IJobStatusService;
import com.licenta.databasemicroservice.business.model.JobStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/api/job-status")
public class JobStatusController {

    @Autowired
    private IJobStatusService jobStatusService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<JobStatusDTO> getAllJobStatus() {

        return jobStatusService.getJobStatusList();
    }

    @RequestMapping(value="/{jobStatusId}", method=RequestMethod.GET)
    public JobStatusDTO getJobStatus(@Min(1) @PathVariable Integer jobStatusId) {

        return jobStatusService.getJobStatus(jobStatusId);
    }
}
