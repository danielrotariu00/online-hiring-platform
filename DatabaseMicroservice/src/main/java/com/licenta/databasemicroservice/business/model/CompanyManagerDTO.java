package com.licenta.databasemicroservice.business.model;

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
public class CompanyManagerDTO {

    private Long id;
    @Min(1)
    private Long companyId;
    @Min(1)
    private Long managerId;
}
