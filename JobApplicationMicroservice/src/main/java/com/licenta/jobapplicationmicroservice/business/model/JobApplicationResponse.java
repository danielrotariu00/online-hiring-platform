package com.licenta.jobapplicationmicroservice.business.model;

import com.licenta.jobapplicationmicroservice.persistence.document.JobApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationResponse {

    private String id;
    private Long userId;
    private Job job;
    private JobApplicationStatus status;
    private List<Message> messageList;
    private Review review;
    private String updatedAt;
}

