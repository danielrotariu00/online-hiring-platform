package com.licenta.database.business.models.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobResponse {

    private String id;
    private String title;
    private String companyId;
    private String cityName;
    private String countryName;
    private String workType; // on-site, remote, hybrid
    private String jobType; // full-time, part-time, seasonal, temporary
    private String experienceLevel; // internship, entry-level, associate, mid-senior level, director
    private String industry; // IT, etc
    private String description;
    private String createdAt;
}
