package com.licenta.database.business.model.job;

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
    private Integer cityId;
    private Integer countryId;
    private String workType; // on-site, remote, hybrid
    private String jobType; // full-time, part-time, seasonal, temporary
    private String experienceLevel; // internship, entry-level, associate, mid-senior level, director
    private Integer companyIndustryId; // IT, etc
    private Integer industryId; // IT, etc
    private String description;
    private String postedAt;
}
