package com.licenta.database.business.model.companyindustryfollower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyIndustryFollowerRequest {

    @NotEmpty
    private String userId;

    private Integer companyIndustryId;
}
