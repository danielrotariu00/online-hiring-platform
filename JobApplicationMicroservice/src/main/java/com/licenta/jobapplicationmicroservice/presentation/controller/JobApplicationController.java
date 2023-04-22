package com.licenta.jobapplicationmicroservice.presentation.controller;

import com.licenta.jobapplicationmicroservice.business.interfaces.IJobApplicationService;
import com.licenta.jobapplicationmicroservice.business.model.CreateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.model.FileData;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.business.model.Message;
import com.licenta.jobapplicationmicroservice.business.model.RecruiterStatistics;
import com.licenta.jobapplicationmicroservice.business.model.Review;
import com.licenta.jobapplicationmicroservice.business.model.UpdateJobApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/api")
public class JobApplicationController {

    @Autowired
    private IJobApplicationService jobApplicationService;

    @RequestMapping(value="/users/{userId}/job-applications", method = RequestMethod.POST)
    public void createJobApplication(@Min(1) @PathVariable Long userId, @Valid @RequestBody CreateJobApplicationRequest request) {

        jobApplicationService.create(userId, request);
    }

    @RequestMapping(value = {"/job-applications/{jobApplicationId}"}, method = RequestMethod.GET)
    public JobApplicationResponse getById(@PathVariable String jobApplicationId) {

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
            @PathVariable String jobApplicationId,
            @Valid @RequestBody UpdateJobApplicationRequest request) {

        jobApplicationService.update(jobApplicationId, request);
    }

    @RequestMapping(value = "/job-applications/{jobApplicationId}", method = RequestMethod.DELETE)
    public void deleteJobApplication(@PathVariable String jobApplicationId) {

        jobApplicationService.delete(jobApplicationId);
    }

    @RequestMapping(value = "/job-applications/status", method = RequestMethod.GET)
    public Iterable<JobApplicationStatusResponse> getStatus() {

        return jobApplicationService.getStatus();
    }

    @RequestMapping(value = "/job-applications/status/{statusId}", method = RequestMethod.GET)
    public JobApplicationStatusResponse getStatusById(@Min(1) @PathVariable Integer statusId) {

        return jobApplicationService.getStatusById(statusId);
    }

    @RequestMapping(value = "/job-applications/{jobApplicationId}/messages", method = RequestMethod.POST)
    public Message addMessage(@PathVariable String jobApplicationId, @RequestBody Message message) {

        return jobApplicationService.addMessage(jobApplicationId, message);
    }

    @RequestMapping(value = "/users/{userId}/job-applications/{jobApplicationId}/review", method = RequestMethod.PUT)
    public Review updateReview(@PathVariable Long userId, @PathVariable String jobApplicationId, @RequestBody Review review) {

        return jobApplicationService.updateReview(userId, jobApplicationId, review);
    }

    @RequestMapping(value = "/recruiters/{recruiterId}/statistics", method = RequestMethod.GET)
    public RecruiterStatistics getRecruiterStatistics(@PathVariable Long recruiterId) {

        return jobApplicationService.getRecruiterStatistics(recruiterId);
    }

    @RequestMapping(value = "/job-applications/{jobApplicationId}/files", method = RequestMethod.GET)
    public List<FileData> getFileList(@PathVariable String jobApplicationId) {
        return jobApplicationService.getFileList(jobApplicationId);
    }

    @RequestMapping(value = "/job-applications/{jobApplicationId}/files/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String jobApplicationId, @PathVariable String filename) throws IOException {
        Resource file = jobApplicationService.download(jobApplicationId, filename);
        Path path = file.getFile()
                .toPath();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}