package com.licenta.jobapplicationmicroservice.business.model;

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
public class JobApplicationResponse {

    private Long id;
    private Long userId;
    private Long jobId;
    private Integer statusId;
    private String updatedAt;
}

