package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.IJobService;
import com.licenta.database.business.models.job.JobRequest;
import com.licenta.database.business.models.job.JobResponse;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.mappers.JobMapper;
import com.licenta.database.persistence.models.City;
import com.licenta.database.persistence.models.Company;
import com.licenta.database.persistence.models.Country;
import com.licenta.database.persistence.models.Job;
import com.licenta.database.persistence.repositories.JobRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class JobService implements IJobService {

    @Autowired
    private CompanyService companyService;
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

        City city = cityService.getCityOrElseThrowException(request.getCityName());
        Country country = countryService.getCountryOrElseThrowException(request.getCountryName());
        Company company = companyService.getCompanyOrElseThrowException(request.getCompanyId());

        job.setCity(city);
        job.setCountry(country);
        job.setCompany(company);

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
    public void updateJob(String jobId, JobRequest request) {
        Job job = getJobOrElseThrowException(jobId);
        Job newJob = jobMapper.toModel(request);

        City city = cityService.getCityOrElseThrowException(request.getCityName());
        Country country = countryService.getCountryOrElseThrowException(request.getCountryName());
        Company company = companyService.getCompanyOrElseThrowException(request.getCompanyId());

        newJob.setId(jobId);
        newJob.setCreatedAt(job.getCreatedAt());
        newJob.setCity(city);
        newJob.setCountry(country);
        newJob.setCompany(company);

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
