package com.licenta.database.business.models.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class JobRequest {

    @NotEmpty
    private String title;
    @NotEmpty
    private String companyId;
    @NotEmpty
    private String cityName;
    @NotEmpty
    private String countryName;
    @NotEmpty
    private String workType; // on-site, remote, hybrid
    @NotEmpty
    private String jobType; // full-time, part-time, seasonal, temporary
    @NotEmpty
    private String experienceLevel; // internship, entry-level, associate, mid-senior level, director
    @NotEmpty
    private String industry; // IT, etc
    @NotEmpty
    private String description;
}
