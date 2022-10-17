package com.licenta.database.business.model.companyindustry;

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

    private Integer id;
    private Integer industryId;
    private String companyId;
}
