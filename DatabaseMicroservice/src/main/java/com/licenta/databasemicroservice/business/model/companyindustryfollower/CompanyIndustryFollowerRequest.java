package com.licenta.databasemicroservice.business.model.companyindustryfollower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyIndustryFollowerRequest {

    @Min(1)
    private Long userId;
    @Min(1)
    private Long companyIndustryId;
}
