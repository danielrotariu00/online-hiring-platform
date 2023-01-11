package com.licenta.jobapplicationmicroservice.business.service;

import com.licenta.jobapplicationmicroservice.business.interfaces.IDatabaseService;
import com.licenta.jobapplicationmicroservice.business.interfaces.IJobApplicationService;
import com.licenta.jobapplicationmicroservice.business.interfaces.INotificationService;
import com.licenta.jobapplicationmicroservice.business.model.Company;
import com.licenta.jobapplicationmicroservice.business.model.CreateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.model.Job;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.business.model.Notification;
import com.licenta.jobapplicationmicroservice.business.model.UpdateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.util.constants.Constants;
import com.licenta.jobapplicationmicroservice.business.util.exception.ExceptionWithStatus;
import com.licenta.jobapplicationmicroservice.business.util.exception.NotFoundException;
import com.licenta.jobapplicationmicroservice.business.util.mapper.JobApplicationMapper;
import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplication;
import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplicationStatus;
import com.licenta.jobapplicationmicroservice.persistence.repository.JobApplicationRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.licenta.jobapplicationmicroservice.business.util.constants.Constants.INITIAL_STATUS_ID;
import static com.licenta.jobapplicationmicroservice.business.util.constants.Constants.JOB_APPLICATION_NOT_FOUND_MESSAGE;

@Service
public class JobApplicationService implements IJobApplicationService {

    @Autowired
    private IDatabaseService databaseService;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private JobApplicationStatusService jobApplicationStatusService;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    private final JobApplicationMapper jobApplicationMapper = Mappers.getMapper(JobApplicationMapper.class);

    @Override
    public void create(CreateJobApplicationRequest request) {
        verifyUserExists(request.getUserId());

        Optional<JobApplication> jobApplicationOptional = jobApplicationRepository
                .findByUserIdAndJobId(request.getUserId(), request.getJobId());

        if(jobApplicationOptional.isPresent()) {
            throw new ExceptionWithStatus("You already applied to this job", HttpStatus.CONFLICT);
        }

        Job job = databaseService.getJob(request.getJobId());
        JobApplicationStatus status = jobApplicationStatusService.getStatusOrElseThrowException(INITIAL_STATUS_ID);
        JobApplication jobApplication = jobApplicationMapper.toEntity(request);

        jobApplication.setStatus(status);
        jobApplicationRepository.save(jobApplication);

        buildAndSendNotification(
                job.getRecruiterId(),
                jobApplication.getId(),
                String.format(Constants.SUBMITTED_JOB_APPLICATION_TEXT_FORMAT, job.getTitle())
        );
    }

    @Override
    public JobApplicationResponse getById(Long jobApplicationId) {
        JobApplication jobApplication = getJobApplicationOrElseThrowException(jobApplicationId);

        return jobApplicationMapper.toResponse(jobApplication);
    }

    @Override
    public Iterable<JobApplicationResponse> getByUserId(Long userId) {
        verifyUserExists(userId);

        return StreamSupport.stream(jobApplicationRepository.findAllByUserId(userId).spliterator(), false)
                .map(jobApplicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<JobApplicationResponse> getByJobId(Long jobId) {
        verifyJobExists(jobId);

        return StreamSupport.stream(jobApplicationRepository.findAllByJobId(jobId).spliterator(), false)
                .map(jobApplicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Long jobApplicationId, UpdateJobApplicationRequest request) {
        JobApplication jobApplication = getJobApplicationOrElseThrowException(jobApplicationId);
        JobApplicationStatus status = jobApplicationStatusService.getStatusOrElseThrowException(request.getStatusId());

        // TODO: define status change order
        if (status != jobApplication.getStatus()) {
            jobApplication.setStatus(status);
            jobApplicationRepository.save(jobApplication);

            // todo extract constant
            Job job = databaseService.getJob(jobApplication.getJobId());
            Company company = databaseService.getCompany(job.getCompanyId());
            if (status.getName().equals("WITHDRAWN")) {
                buildAndSendNotification(
                        job.getRecruiterId(),
                        jobApplication.getId(),
                        String.format(Constants.WITHDRAWN_JOB_APPLICATION_TEXT_FORMAT, job.getTitle())
                );
            } else {
                buildAndSendNotification(
                        jobApplication.getUserId(),
                        jobApplication.getId(),
                        String.format(Constants.UPDATED_JOB_APPLICATION_TEXT_FORMAT, job.getTitle(), company.getName())
                );
            }
        }
    }

    @Override
    public void delete(Long jobApplicationId) {
        getJobApplicationOrElseThrowException(jobApplicationId);

        jobApplicationRepository.deleteById(jobApplicationId);
    }

    @Override
    public Iterable<JobApplicationStatusResponse> getStatus() {
        return jobApplicationStatusService.getStatus();
    }

    @Override
    public JobApplicationStatusResponse getStatusById(Integer statusId) {
        return jobApplicationStatusService.getStatusById(statusId);
    }

    private JobApplication getJobApplicationOrElseThrowException(Long jobApplicationId) {

        return jobApplicationRepository.findById(jobApplicationId).orElseThrow(
                () -> new NotFoundException(String.format(JOB_APPLICATION_NOT_FOUND_MESSAGE, jobApplicationId))
        );
    }

    private void verifyUserExists(Long userId) {
        databaseService.verifyUserExists(userId);
    }

    private void verifyJobExists(Long jobId) {
        databaseService.getJob(jobId);
    }

    private void buildAndSendNotification(Long userId, Long jobApplicationId, String text) {
        Notification notification = Notification.builder()
                .userId(userId)
                .jobApplicationId(jobApplicationId)
                .text(text)
                .timestamp(LocalDateTime.now())
                .build();

        notificationService.sendNotification(notification);
    }
}
