package com.licenta.jobapplicationmicroservice.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    private Long id;
    private String title;
    private Long recruiterId;
    private Long companyId;
    private Integer cityId;
    private Integer countryId;
    private Integer workTypeId; // on-site, remote, hybrid
    private Integer jobTypeId; // full-time, part-time, seasonal, temporary
    private Integer experienceLevelId; // internship, entry-level, associate, mid-senior level, director
    private Integer companyIndustryId; // IT, etc
    private Integer industryId; // IT, etc
    private String description;
    private Integer jobStatusId;
    private String postedAt;
}
