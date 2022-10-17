package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.IJobService;
import com.licenta.database.business.model.job.JobRequest;
import com.licenta.database.business.model.job.JobResponse;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.mappers.JobMapper;
import com.licenta.database.persistence.entities.City;
import com.licenta.database.persistence.entities.CompanyIndustry;
import com.licenta.database.persistence.entities.Job;
import com.licenta.database.persistence.repositories.JobRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class JobService implements IJobService {

    @Autowired
    private CompanyIndustryService companyIndustryService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private JobRepository jobRepository;

    private final JobMapper jobMapper = Mappers.getMapper(JobMapper.class);

    static final String JOB_NOT_FOUND_MESSAGE = "Job with id <%s> does not exist.";

    @Override
    public void createJob(JobRequest request) {
        Job job = jobMapper.toModel(request);

        City city = cityService.getCityOrElseThrowException(request.getCityId());
        CompanyIndustry companyIndustry =
                companyIndustryService.getCompanyIndustryOrElseThrowException(request.getCompanyIndustryId());

        job.setCity(city);
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
        CompanyIndustry companyIndustry =
                companyIndustryService.getCompanyIndustryOrElseThrowException(request.getCompanyIndustryId());

        newJob.setId(jobId);
        newJob.setPostedAt(job.getPostedAt());
        newJob.setCity(city);
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
