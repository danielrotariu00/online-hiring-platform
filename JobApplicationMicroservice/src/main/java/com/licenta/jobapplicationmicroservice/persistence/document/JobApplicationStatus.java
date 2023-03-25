package com.licenta.jobapplicationmicroservice.persistence.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "job_application_status")
public class JobApplicationStatus {

    @Id
    private Integer id;

    private String name;
}
