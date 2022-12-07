package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.job.JobRequest;
import com.licenta.databasemicroservice.business.model.job.JobResponse;
import com.licenta.databasemicroservice.persistence.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(imports = {Date.class, SimpleDateFormat.class})
public interface JobMapper {

    Job toModel(JobRequest request);

    @Mapping(target="recruiterId", source="recruiter.id")
    @Mapping(target="cityId", source="city.id")
    @Mapping(target="countryId", source="city.country.id")
    @Mapping(target="jobTypeId", source="jobType.id")
    @Mapping(target="jobStatusId", source="jobStatus.id")
    @Mapping(target="workTypeId", source="workType.id")
    @Mapping(target="experienceLevelId", source="experienceLevel.id")
    @Mapping(target="companyId", source="companyIndustry.company.id")
    @Mapping(target="industryId", source="companyIndustry.industry.id")
    @Mapping(target="companyIndustryId", source="companyIndustry.id")
    @Mapping(target="postedAt", expression = "java(new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(job.getPostedAt()))")
    JobResponse toResponse(Job job);
}
