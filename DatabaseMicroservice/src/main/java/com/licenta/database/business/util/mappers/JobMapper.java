package com.licenta.database.business.util.mappers;

import com.licenta.database.business.model.job.JobRequest;
import com.licenta.database.business.model.job.JobResponse;
import com.licenta.database.persistence.entities.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Date;
import java.util.UUID;

@Mapper(imports = {UUID.class, Date.class})
public interface JobMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "postedAt", expression = "java(new Date().toString())")
    Job toModel(JobRequest request);

    @Mapping(target="companyIndustryId", source="companyIndustry.id")
    @Mapping(target="companyId", source="companyIndustry.company.id")
    @Mapping(target="industryId", source="companyIndustry.industry.id")
    @Mapping(target="cityId", source="city.id")
    @Mapping(target="countryId", source="city.country.id")
    JobResponse toResponse(Job job);
}
