package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IJobService;
import com.licenta.databasemicroservice.business.model.job.JobRequest;
import com.licenta.databasemicroservice.business.model.job.JobResponse;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.JobMapper;
import com.licenta.databasemicroservice.persistence.entity.City;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;
import com.licenta.databasemicroservice.persistence.entity.Job;
import com.licenta.databasemicroservice.persistence.entity.JobType;
import com.licenta.databasemicroservice.persistence.entity.WorkType;
import com.licenta.databasemicroservice.persistence.repository.JobRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class JobService implements IJobService {

    @Autowired
    private CityService cityService;
    @Autowired
    private JobTypeService jobTypeService;
    @Autowired
    private WorkTypeService workTypeService;
    @Autowired
    private ExperienceLevelService experienceLevelService;
    @Autowired
    private CompanyIndustryService companyIndustryService;

    @Autowired
    private JobRepository jobRepository;

    private final JobMapper jobMapper = Mappers.getMapper(JobMapper.class);

    private static final String JOB_NOT_FOUND_MESSAGE = "Job with id <%s> does not exist.";

    @Override
    public void createJob(JobRequest request) {
        Job job = jobMapper.toModel(request);

        City city = cityService.getCityOrElseThrowException(request.getCityId());
        JobType jobType = jobTypeService.getJobTypeOrElseThrowException(request.getJobTypeId());
        WorkType workType = workTypeService.getWorkTypeOrElseThrowException(request.getWorkTypeId());
        ExperienceLevel experienceLevel = experienceLevelService.getExperienceLevelOrElseThrowException(request.getExperienceLevelId());
        CompanyIndustry companyIndustry = companyIndustryService.getCompanyIndustryOrElseThrowException(request.getCompanyIndustryId());

        job.setCity(city);
        job.setJobType(jobType);
        job.setWorkType(workType);
        job.setExperienceLevel(experienceLevel);
        job.setCompanyIndustry(companyIndustry);

        jobRepository.save(job);
    }

    @Override
    public JobResponse getJob(String jobId) {
        Job job = getJobOrElseThrowException(jobId);

        return jobMapper.toResponse(job);
    }

    @Override
    public Iterable<JobResponse> getJobs() {

        return StreamSupport.stream(jobRepository.findAll().spliterator(), false)
                .map(jobMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<JobResponse> getCompanyIndustryJobs(Integer companyIndustryId) {

        companyIndustryService.getCompanyIndustryOrElseThrowException(companyIndustryId);

        return jobRepository.findJobsByCompanyIndustryId(companyIndustryId).stream()
                .map(jobMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateJob(String jobId, JobRequest request) {
        Job job = getJobOrElseThrowException(jobId);
        Job newJob = jobMapper.toModel(request);

        City city = cityService.getCityOrElseThrowException(request.getCityId());
        JobType jobType = jobTypeService.getJobTypeOrElseThrowException(request.getJobTypeId());
        WorkType workType = workTypeService.getWorkTypeOrElseThrowException(request.getWorkTypeId());
        ExperienceLevel experienceLevel = experienceLevelService.getExperienceLevelOrElseThrowException(request.getExperienceLevelId());
        CompanyIndustry companyIndustry = companyIndustryService.getCompanyIndustryOrElseThrowException(request.getCompanyIndustryId());

        newJob.setId(jobId);
        newJob.setPostedAt(job.getPostedAt());
        newJob.setCity(city);
        newJob.setJobType(jobType);
        newJob.setWorkType(workType);
        newJob.setExperienceLevel(experienceLevel);
        newJob.setCompanyIndustry(companyIndustry);

        jobRepository.save(newJob);
    }

    @Override
    public void deleteJob(String jobId) {

        getJobOrElseThrowException(jobId);

        jobRepository.deleteById(jobId);
    }

    private Job getJobOrElseThrowException(String jobId) {

        return jobRepository.findById(jobId).orElseThrow(
                () -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId))
        );
    }
}
