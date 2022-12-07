package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IJobStatusService;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.persistence.entity.JobStatus;
import com.licenta.databasemicroservice.persistence.repository.JobStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobStatusService implements IJobStatusService {

    @Autowired
    private JobStatusRepository jobStatusRepository;

    static final String JOB_STATUS_NOT_FOUND_MESSAGE = "JobStatus with id <%s> does not exist.";

    @Override
    public JobStatus getJobStatusOrElseThrowException(Integer jobStatusId) {

        return jobStatusRepository.findById(jobStatusId).orElseThrow(
                () -> new NotFoundException(String.format(JOB_STATUS_NOT_FOUND_MESSAGE, jobStatusId))
        );
    }
}
