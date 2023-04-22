package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICityService;
import com.licenta.databasemicroservice.business.interfaces.ICompanyIndustryService;
import com.licenta.databasemicroservice.business.interfaces.IExperienceLevelService;
import com.licenta.databasemicroservice.business.interfaces.IJobService;
import com.licenta.databasemicroservice.business.interfaces.IJobStatusService;
import com.licenta.databasemicroservice.business.interfaces.IJobTypeService;
import com.licenta.databasemicroservice.business.interfaces.IWorkTypeService;
import com.licenta.databasemicroservice.business.model.job.JobRequest;
import com.licenta.databasemicroservice.business.model.job.JobResponse;
import com.licenta.databasemicroservice.business.util.Constants;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.JobMapper;
import com.licenta.databasemicroservice.persistence.entity.City;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;
import com.licenta.databasemicroservice.persistence.entity.Job;
import com.licenta.databasemicroservice.persistence.entity.JobStatus;
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
    private ICityService cityService;
    @Autowired
    private IJobTypeService jobTypeService;
    @Autowired
    private IWorkTypeService workTypeService;
    @Autowired
    private IExperienceLevelService experienceLevelService;
    @Autowired
    private ICompanyIndustryService companyIndustryService;
    @Autowired
    private IJobStatusService jobStatusService;

    @Autowired
    private JobRepository jobRepository;

    private final JobMapper jobMapper = Mappers.getMapper(JobMapper.class);

    private static final String JOB_NOT_FOUND_MESSAGE = "Job with id <%s> does not exist.";

    @Override
    public void createJob(JobRequest request) {
        Job job = jobMapper.toModel(request);

        CompanyIndustry companyIndustry = companyIndustryService.getCompanyIndustryOrElseThrowException(request.getCompanyIndustryId());
        City city = cityService.getCityOrElseThrowException(request.getCityId());
        WorkType workType = workTypeService.getWorkTypeOrElseThrowException(request.getWorkTypeId());
        JobType jobType = jobTypeService.getJobTypeOrElseThrowException(request.getJobTypeId());
        ExperienceLevel experienceLevel = experienceLevelService.getExperienceLevelOrElseThrowException(request.getExperienceLevelId());
        JobStatus jobStatus = jobStatusService.getJobStatusOrElseThrowException(Constants.INITIAL_JOB_STATUS_ID);

        job.setRecruiterId(request.getRecruiterId());
        job.setCompanyIndustry(companyIndustry);
        job.setCity(city);
        job.setWorkType(workType);
        job.setJobType(jobType);
        job.setExperienceLevel(experienceLevel);
        job.setJobStatus(jobStatus);

        jobRepository.save(job);
    }

    @Override
    public JobResponse getJob(Long jobId) {
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
    public Iterable<JobResponse> getCompanyIndustryJobs(Long companyIndustryId) {

        companyIndustryService.getCompanyIndustryOrElseThrowException(companyIndustryId);

        return jobRepository.findJobsByCompanyIndustryId(companyIndustryId).stream()
                .map(jobMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateJob(Long jobId, JobRequest request) {
        Job job = getJobOrElseThrowException(jobId);
        Job newJob = jobMapper.toModel(request);

        City city = cityService.getCityOrElseThrowException(request.getCityId());
        JobType jobType = jobTypeService.getJobTypeOrElseThrowException(request.getJobTypeId());
        WorkType workType = workTypeService.getWorkTypeOrElseThrowException(request.getWorkTypeId());
        ExperienceLevel experienceLevel = experienceLevelService.getExperienceLevelOrElseThrowException(request.getExperienceLevelId());
        CompanyIndustry companyIndustry = companyIndustryService.getCompanyIndustryOrElseThrowException(request.getCompanyIndustryId());
        JobStatus jobStatus = jobStatusService.getJobStatusOrElseThrowException(request.getJobStatusId());

        newJob.setId(jobId);
        newJob.setPostedAt(job.getPostedAt());
        newJob.setRecruiterId(request.getRecruiterId());
        newJob.setCity(city);
        newJob.setJobType(jobType);
        newJob.setWorkType(workType);
        newJob.setExperienceLevel(experienceLevel);
        newJob.setCompanyIndustry(companyIndustry);
        newJob.setJobStatus(jobStatus);

        jobRepository.save(newJob);
    }

    @Override
    public void deleteJob(Long jobId) {

        getJobOrElseThrowException(jobId);

        jobRepository.deleteById(jobId);
    }

    private Job getJobOrElseThrowException(Long jobId) {

        return jobRepository.findById(jobId).orElseThrow(
                () -> new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId))
        );
    }
}
