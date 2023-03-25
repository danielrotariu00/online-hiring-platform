package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IJobTypeService;
import com.licenta.databasemicroservice.business.model.jobtype.JobTypeResponse;
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
@RequestMapping(value="/job-types")
public class JobTypeController {

    @Autowired
    private IJobTypeService jobTypeService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<JobTypeResponse> getAllJobTypes() {

        return jobTypeService.getJobTypes();
    }

    @RequestMapping(value="/{jobTypeId}", method=RequestMethod.GET)
    public JobTypeResponse getJobType(@Min(1) @PathVariable Integer jobTypeId) {

        return jobTypeService.getJobType(jobTypeId);
    }
}
