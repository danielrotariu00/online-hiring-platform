package com.licenta.databasemicroservice.business.model.company;

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
public class CreateCompanyRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String photo;
    @NotEmpty
    private String description;
    @Min(1)
    private Integer cityId;
    @NotEmpty
    private String website;
}
