package com.licenta.databasemicroservice.business.model.companyindustry;

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
public class CompanyIndustryResponse {

    private Long id;
    private Integer industryId;
    private Long companyId;
}
