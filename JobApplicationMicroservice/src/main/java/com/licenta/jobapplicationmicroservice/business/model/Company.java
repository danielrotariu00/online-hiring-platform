package com.licenta.jobapplicationmicroservice.business.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Company {

    private Long id;
    private String name;
}
