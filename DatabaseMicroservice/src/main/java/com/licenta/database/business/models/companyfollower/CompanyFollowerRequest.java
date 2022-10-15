package com.licenta.database.business.models.companyfollower;

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
public class CompanyFollowerRequest {

    private String userId;
    private String companyId;
}
