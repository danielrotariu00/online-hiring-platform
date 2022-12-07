package com.licenta.databasemicroservice.business.model.companyindustry;

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
public class CompanyIndustryRequest {

    @Min(1)
    private Long companyId;
    @Min(1)
    private Integer industryId;
}
