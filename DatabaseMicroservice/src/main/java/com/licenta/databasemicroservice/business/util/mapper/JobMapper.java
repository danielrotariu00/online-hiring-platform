package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.job.JobRequest;
import com.licenta.databasemicroservice.business.model.job.JobResponse;
import com.licenta.databasemicroservice.persistence.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Mapper(imports = {UUID.class, Date.class, SimpleDateFormat.class})
public interface JobMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "postedAt", expression = "java(new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(new Date()))")
    Job toModel(JobRequest request);

    @Mapping(target="cityId", source="city.id")
    @Mapping(target="countryId", source="city.country.id")
    @Mapping(target="jobTypeId", source="jobType.id")
    @Mapping(target="workTypeId", source="workType.id")
    @Mapping(target="experienceLevelId", source="experienceLevel.id")
    @Mapping(target="companyId", source="companyIndustry.company.id")
    @Mapping(target="industryId", source="companyIndustry.industry.id")
    @Mapping(target="companyIndustryId", source="companyIndustry.id")
    JobResponse toResponse(Job job);
}
