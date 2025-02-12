package com.licenta.databasemicroservice.business.model.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {

    @NotEmpty
    private String title;
    @Min(1)
    private Long recruiterId;
    @Min(1)
    private Long companyIndustryId;
    @Min(1)
    private Integer cityId;
    @Min(1)
    private Integer workTypeId;
    @Min(1)
    private Integer jobTypeId;
    @Min(1)
    private Integer experienceLevelId;
    @NotEmpty
    private String description;

    private Integer jobStatusId;
}
