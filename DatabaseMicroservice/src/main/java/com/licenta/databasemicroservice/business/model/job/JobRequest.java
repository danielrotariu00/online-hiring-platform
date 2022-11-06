package com.licenta.databasemicroservice.business.model.job;

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
    @Min(1)
    private Integer workTypeId;
    @Min(1)
    private Integer jobTypeId;
    @Min(1)
    private Integer experienceLevelId;
    @NotEmpty
    private String description;
}
