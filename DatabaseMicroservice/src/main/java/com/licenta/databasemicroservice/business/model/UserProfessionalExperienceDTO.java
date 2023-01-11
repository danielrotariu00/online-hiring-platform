package com.licenta.databasemicroservice.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfessionalExperienceDTO {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long companyId;

    @NotEmpty
    private String jobTitle;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    private String description;
}
