package com.licenta.database.business.models.companyfollower;

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
public class CompanyFollowerRequest {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String companyId;
}
