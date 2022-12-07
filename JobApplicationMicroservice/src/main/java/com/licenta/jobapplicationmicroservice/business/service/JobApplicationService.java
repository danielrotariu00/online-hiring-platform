package com.licenta.jobapplicationmicroservice.business.service;

import com.licenta.jobapplicationmicroservice.business.interfaces.IDatabaseService;
import com.licenta.jobapplicationmicroservice.business.interfaces.IJobApplicationService;
import com.licenta.jobapplicationmicroservice.business.interfaces.INotificationService;
import com.licenta.jobapplicationmicroservice.business.model.CreateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.model.Job;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationResponse;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.business.model.Notification;
import com.licenta.jobapplicationmicroservice.business.model.NotificationType;
import com.licenta.jobapplicationmicroservice.business.model.UpdateJobApplicationRequest;
import com.licenta.jobapplicationmicroservice.business.util.exception.NotFoundException;
import com.licenta.jobapplicationmicroservice.business.util.mapper.JobApplicationMapper;
import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplication;
import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplicationStatus;
import com.licenta.jobapplicationmicroservice.persistence.repository.JobApplicationRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        Job job = databaseService.getJob(request.getJobId());
        JobApplicationStatus status = jobApplicationStatusService.getStatusOrElseThrowException(INITIAL_STATUS_ID);
        JobApplication jobApplication = jobApplicationMapper.toEntity(request);

        jobApplication.setStatus(status);
        jobApplicationRepository.save(jobApplication);

        buildAndSendNotification(
                job.getRecruiterId(),
                jobApplication.getId(),
                NotificationType.JOB_APPLICATION_SUBMITTED
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
            if (status.getName().equals("WITHDRAWN")) {
                Job job = databaseService.getJob(jobApplication.getJobId());

                buildAndSendNotification(
                        job.getRecruiterId(),
                        jobApplication.getId(),
                        NotificationType.JOB_APPLICATION_WITHDRAWN
                );
            } else {
                buildAndSendNotification(
                        jobApplication.getUserId(),
                        jobApplication.getId(),
                        NotificationType.JOB_APPLICATION_UPDATED
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

    private void buildAndSendNotification(Long userId, Long jobApplicationId, NotificationType type) {
        Notification notification = Notification.builder()
                .userId(userId)
                .jobApplicationId(jobApplicationId)
                .type(type)
                .timestamp(LocalDateTime.now().toString())
                .build();

        notificationService.sendNotification(notification);
    }
}
