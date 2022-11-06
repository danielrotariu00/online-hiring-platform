package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.persistence.entity.JobType;
import com.licenta.databasemicroservice.persistence.repository.JobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobTypeService {

    @Autowired
    private JobTypeRepository jobTypeRepository;

    static final String JOB_TYPE_NOT_FOUND_MESSAGE = "JobType with id <%s> does not exist.";

    public JobType getJobTypeOrElseThrowException(Integer jobTypeId) {

        return jobTypeRepository.findById(jobTypeId).orElseThrow(
                () -> new NotFoundException(String.format(JOB_TYPE_NOT_FOUND_MESSAGE, jobTypeId))
        );
    }
}
