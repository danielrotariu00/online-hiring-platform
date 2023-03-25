package com.licenta.jobapplicationmicroservice.business.service;

import com.licenta.jobapplicationmicroservice.business.interfaces.IJobApplicationStatusService;
import com.licenta.jobapplicationmicroservice.business.model.JobApplicationStatusResponse;
import com.licenta.jobapplicationmicroservice.business.util.exception.NotFoundException;
import com.licenta.jobapplicationmicroservice.business.util.mapper.JobApplicationStatusMapper;
import com.licenta.jobapplicationmicroservice.persistence.document.JobApplicationStatus;
import com.licenta.jobapplicationmicroservice.persistence.repository.JobApplicationStatusRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.licenta.jobapplicationmicroservice.business.util.constants.Constants.STATUS_NOT_FOUND_MESSAGE;

@Service
public class JobApplicationStatusService implements IJobApplicationStatusService {

    @Autowired
    private JobApplicationStatusRepository jobApplicationStatusRepository;

    private final JobApplicationStatusMapper jobApplicationMapper = Mappers.getMapper(JobApplicationStatusMapper.class);

    @Override
    public Iterable<JobApplicationStatusResponse> getStatus() {

        return StreamSupport.stream(jobApplicationStatusRepository.findAll().spliterator(), false)
                .map(jobApplicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobApplicationStatusResponse getStatusById(Integer statusId) {

        return jobApplicationMapper.toResponse(getStatusOrElseThrowException(statusId));
    }

    @Override
    public JobApplicationStatus getStatusOrElseThrowException(Integer statusId) {

        return jobApplicationStatusRepository.findById(statusId).orElseThrow(
                () -> new NotFoundException(String.format(STATUS_NOT_FOUND_MESSAGE, statusId))
        );
    }
}
