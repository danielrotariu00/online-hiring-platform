package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IJobTypeService;
import com.licenta.databasemicroservice.business.model.JobTypeDTO;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.JobTypeMapper;
import com.licenta.databasemicroservice.persistence.entity.JobType;
import com.licenta.databasemicroservice.persistence.repository.JobTypeRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobTypeService implements IJobTypeService {

    @Autowired
    private JobTypeRepository jobTypeRepository;

    private final JobTypeMapper jobTypeMapper = Mappers.getMapper(JobTypeMapper.class);

    static final String JOB_TYPE_NOT_FOUND_MESSAGE = "JobType with id <%s> does not exist.";

    @Override
    public JobType getJobTypeOrElseThrowException(Integer jobTypeId) {

        return jobTypeRepository.findById(jobTypeId).orElseThrow(
                () -> new NotFoundException(String.format(JOB_TYPE_NOT_FOUND_MESSAGE, jobTypeId))
        );
    }

    @Override
    public Iterable<JobTypeDTO> getJobTypes() {
        return StreamSupport.stream(jobTypeRepository.findAll().spliterator(), false)
                .map(jobTypeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobTypeDTO getJobType(Integer jobTypeId) {
        JobType jobType = getJobTypeOrElseThrowException(jobTypeId);

        return jobTypeMapper.toResponse(jobType);
    }
}
