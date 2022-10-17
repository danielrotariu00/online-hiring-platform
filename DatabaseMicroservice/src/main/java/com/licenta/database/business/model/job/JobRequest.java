package com.licenta.database.business.model.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class JobRequest {

    @NotEmpty
    private String title;
    @Min(1)
    private Integer companyIndustryId;
    @Min(1)
    private Integer cityId;
    @Min(1)
    private Integer countryId;
    @NotEmpty
    private String workType; // on-site, remote, hybrid
    @NotEmpty
    private String jobType; // full-time, part-time, seasonal, temporary
    @NotEmpty
    private String experienceLevel; // internship, entry-level, associate, mid-senior level, director
    @NotEmpty
    private String description;
}
