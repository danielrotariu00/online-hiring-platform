package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IJobStatusService;
import com.licenta.databasemicroservice.business.model.jobstatus.JobStatusResponse;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.JobStatusMapper;
import com.licenta.databasemicroservice.persistence.entity.JobStatus;
import com.licenta.databasemicroservice.persistence.repository.JobStatusRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobStatusService implements IJobStatusService {

    @Autowired
    private JobStatusRepository jobStatusRepository;

    private final JobStatusMapper jobStatusMapper = Mappers.getMapper(JobStatusMapper.class);

    static final String JOB_STATUS_NOT_FOUND_MESSAGE = "JobStatus with id <%s> does not exist.";

    @Override
    public JobStatus getJobStatusOrElseThrowException(Integer jobStatusId) {

        return jobStatusRepository.findById(jobStatusId).orElseThrow(
                () -> new NotFoundException(String.format(JOB_STATUS_NOT_FOUND_MESSAGE, jobStatusId))
        );
    }

    @Override
    public Iterable<JobStatusResponse> getJobStatusList() {
        return StreamSupport.stream(jobStatusRepository.findAll().spliterator(), false)
                .map(jobStatusMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobStatusResponse getJobStatus(Integer jobStatusId) {
        JobStatus jobStatus = getJobStatusOrElseThrowException(jobStatusId);

        return jobStatusMapper.toResponse(jobStatus);
    }
}
